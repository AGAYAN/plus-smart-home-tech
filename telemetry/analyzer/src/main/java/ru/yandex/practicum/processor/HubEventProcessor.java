package ru.yandex.practicum.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.time.Duration;
import java.util.Collections;


@Component
@Slf4j
public class HubEventProcessor implements Runnable {

    private  KafkaConsumer<String, HubEventAvro> consumer;

    @Override
    public void run() {
        while (true) {
            try {
                ConsumerRecords<String, HubEventAvro> records = consumer.poll(Duration.ofMillis(100));
                consumer.subscribe(Collections.singleton("telemetry.hubs.v1"));
                for (ConsumerRecord<String, HubEventAvro> record : records) {
                    processHubEvent(record.value());
                }
            } catch (Exception e) {
                log.error("Ошибка при обработке событий хабов: ", e);
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
        // Логика обработки добавления устройства
    }

    private void processDeviceRemovedEvent(String hubId, DeviceRemovedEventAvro event) {
        log.info("Устройство удалено из хаба {}: ID={}", hubId, event.getId());
        // Логика обработки удаления устройств
    }

    private void processScenarioAddedEvent(String hubId, ScenarioAddedEventAvro event) {
        log.info("Сценарий добавлен в хаб {}: Название={}, Условий={}, Действий={}",
                hubId, event.getName(), event.getConditions().size(), event.getActions().size());

        // Обработка условий сценария
        for (ScenarioConditionAvro condition : event.getConditions()) {
            log.info("Условие: сенсор={}, тип={}, операция={}, значение={}",
                    condition.getSensorId(), condition.getType(), condition.getOperation(), condition.getValue());
        }

        // Обработка действий сценария
        for (DeviceActionAvro action : event.getActions()) {
            log.info("Действие: сенсор={}, тип={}, значение={}",
                    action.getSensorId(), action.getType(), action.getValue());
        }


    }

    private void processScenarioRemovedEvent(String hubId, ScenarioRemovedEventAvro event) {
        log.info("Сценарий удален из хаба {}: Название={}", hubId, event.getName());
        // Логика обработки удаления сценария
    }
}