package ru.yandex.practicum.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.*;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Duration;
import java.util.Collections;

@Component
@Slf4j
public class SnapshotProcessor implements Runnable {
    private KafkaConsumer<String, SensorsSnapshotAvro> consumer;

    @Override
    public void run() {
        while (true) {
            try {
                ConsumerRecords<String, SensorsSnapshotAvro> records = consumer.poll(Duration.ofMillis(100));
                consumer.subscribe(Collections.singleton("telemetry.hubs.v1"));
                for (ConsumerRecord<String, SensorsSnapshotAvro> record : records) {
                    processSnapshot(record.value());
                }
            } catch (Exception e) {
                log.error("Ошибка при обработке событий хабов: ", e);
            }
        }
    }

    private void processSnapshot(SensorsSnapshotAvro snapshot) {
        log.info("Обработка снапшота для хаба: {}", snapshot.getHubId());

        switch (snapshot.get(snapshot.getHubId())) {
            case MotionSensorEvent mse -> processMotionSensor(snapshot.getHubId(), mse);
            case TemperatureSensorEvent tse -> processTemperatureSensor(snapshot.getHubId(), tse);
            case LightSensorEvent lse -> processLightSensor(snapshot.getHubId(), lse);
            case ClimateSensorEvent cse -> processClimateSensor(snapshot.getHubId(), cse);
            case SwitchSensorEvent sse -> processSwitchSensor(snapshot.getHubId(), sse);
            default -> log.warn("Получено событие неизвестного типа: {}", snapshot);
        }
    }

    private void processMotionSensor(String hubId, MotionSensorEvent event) {
        log.info("Motion Sensor [hubId={}]: Motion={}, Link Quality={}, Voltage={}",
                hubId, event.getMotion(), event.getLinkQuality(), event.getVoltage());
        // Логика обработки событий движения
    }

    private void processTemperatureSensor(String hubId, TemperatureSensorEvent event) {
        log.info("Temperature Sensor [hubId={}]: Temp (C)={}, Temp (F)={}",
                hubId, event.getTemperatureC(), event.getTemperatureF());
        // Логика обработки температурных данных
    }

    private void processLightSensor(String hubId, LightSensorEvent event) {
        log.info("Light Sensor [hubId={}]: Luminosity={}, Link Quality={}",
                hubId, event.getLuminosity(), event.getLinkQuality());
        // Логика обработки световых данных
    }

    private void processClimateSensor(String hubId, ClimateSensorEvent event) {
        log.info("Climate Sensor [hubId={}]: Temp (C)={}, Humidity={}, CO2 Level={}",
                hubId, event.getTemperatureC(), event.getHumidity(), event.getCo2Level());
        // Логика обработки климатических данных
    }

    private void processSwitchSensor(String hubId, SwitchSensorEvent event) {
        log.info("Switch Sensor [hubId={}]: State={}", hubId, event.getState());
        // Логика обработки данных переключателя
    }

    public void start() {
        run();
    }
}
