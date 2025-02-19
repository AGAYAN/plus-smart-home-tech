package ru.yandex.practicum.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.KafkaConsumerConfig;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.model.Scenarios;
import ru.yandex.practicum.model.Sensor;
import ru.yandex.practicum.repository.ScenarioRepository;
import ru.yandex.practicum.repository.SensorRepository;

import java.time.Duration;
import java.util.Collections;
import java.util.Optional;

@Component
@Slf4j
public class HubEventProcessor implements Runnable {

    private final KafkaConsumer<String, HubEventAvro> consumer;
    private final SensorRepository sensorRepository;
    private final ScenarioRepository scenarioRepository;

    public HubEventProcessor(KafkaConsumerConfig kafkaConsumerConfig, SensorRepository sensorRepository, ScenarioRepository scenarioRepository) {
        this.consumer = kafkaConsumerConfig.hubEventsConsumer();
        this.sensorRepository = sensorRepository;
        this.scenarioRepository = scenarioRepository;

        // Добавляем shutdown hook для корректного завершения
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutdown hook triggered. Calling consumer.wakeup()");
            consumer.wakeup();
        }));
    }

    @Override
    public void run() {
        try {
            consumer.subscribe(Collections.singleton("telemetry.hubs.v1"));

            while (!Thread.currentThread().isInterrupted()) {
                ConsumerRecords<String, HubEventAvro> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, HubEventAvro> record : records) {
                    log.info("Received event: {}", record.value());
                    processHubEvent(record.value());
                }

                // Коммитим оффсеты после обработки
                try {
                    consumer.commitSync();
                    log.info("Offsets committed successfully");
                } catch (Exception e) {
                    log.error("Failed to commit offsets", e);
                }
            }
        }catch (WakeupException ignored) {
            // Исключение возникает при вызове wakeup(), его можно игнорировать
            log.info("Consumer woken up for shutdown");
        } catch (Exception e) {
            log.error("Error processing hub events", e);
        } finally {
            try {
                consumer.close();
                log.info("Consumer closed");
            } catch (Exception e) {
                log.error("Error closing consumer", e);
            }
        }
    }

    private void processHubEvent(HubEventAvro hubEvent) {
        String hubId = hubEvent.getHubId();

        switch (hubEvent.getPayload()) {
            case DeviceAddedEventAvro dae -> processDeviceAddedEvent(hubId, dae);
            case DeviceRemovedEventAvro dre -> processDeviceRemovedEvent(hubId, dre);
            case ScenarioAddedEventAvro sae -> processScenarioAddedEvent(hubId, sae);
            case ScenarioRemovedEventAvro sre -> processScenarioRemovedEvent(hubId, sre);
            default -> log.warn("Получено событие неизвестного типа: {}", hubEvent);
        }
    }

    private void processDeviceAddedEvent(String hubId, DeviceAddedEventAvro event) {
        log.info("Устройство добавлено в хаб {}: ID={}, Тип={}", hubId, event.getId(), event.getType());
        if (sensorRepository.existsByIdInAndHubId(Collections.singleton(event.getId()), hubId)) {
            log.info("Сенсор с ID={} уже существует в хабе {}", event.getId(), hubId);
        } else {
            // Если сенсор еще не существует, сохраняем его
            sensorRepository.save(new Sensor(event.getId(), hubId, event.getType()));
            log.info("Сенсор с ID={} добавлен в хаб {}", event.getId(), hubId);
        }
        // Логика обработки добавления устройства
    }

    private void processDeviceRemovedEvent(String hubId, DeviceRemovedEventAvro event) {
        log.info("Устройство удалено из хаба {}: ID={}", hubId, event.getId());
        Optional<Sensor> sensorOpt = sensorRepository.findByIdAndHubId(event.getId(), hubId);
        if (sensorOpt.isPresent()) {
            sensorRepository.deleteById(event.getId());
            log.info("Сенсор с ID={} удален из хаба {}", event.getId(), hubId);
        } else {
            log.warn("Сенсор с ID={} не найден в хабе {}", event.getId(), hubId);
        }
        // Логика обработки удаления устройств
    }

    private void processScenarioAddedEvent(String hubId, ScenarioAddedEventAvro event) {
        log.info("Сценарий добавлен в хаб {}: Название={}, Условий={}, Действий={}",
                hubId, event.getName(), event.getConditions().size(), event.getActions().size());
        // Сохранение сценария
        Scenarios scenario = new Scenarios(hubId, event.getName());
        scenarioRepository.save(scenario);

    }

    private void processScenarioRemovedEvent(String hubId, ScenarioRemovedEventAvro event) {
        log.info("Сценарий удален из хаба {}: Название={}", hubId, event.getName());
        scenarioRepository.findByHubIdAndName(hubId, event.getName())
                .ifPresent(scenarioRepository::delete);
        // Логика обработки удаления сценария
    }
}