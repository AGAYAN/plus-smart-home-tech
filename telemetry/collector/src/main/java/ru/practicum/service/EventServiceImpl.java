package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.practicum.event.hubs.DeviceAddedEvent;
import ru.practicum.event.hubs.DeviceRemovedEvent;
import ru.practicum.event.sensor.*;
import ru.practicum.mapper.HubMapperEvent;
import ru.practicum.mapper.SensorMapperEvent;
import ru.practicum.model.HubEvent;
import ru.practicum.model.SensorEvent;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService, AutoCloseable {
    @Value("${topics.sensor}")
    private String sensorTopic;

    @Value("${topics.hubs}")
    private String hubTopic;

    final KafkaProducer<String, SpecificRecordBase> kafkaProducer;

    @Override
    public <T extends HubEvent> void collectHubEvent(T event) {
        String hubId = event.getHubId();
        long eventTimestamp = event.getTimestamp().toEpochMilli();

        String topic = determineTopic(hubId);
        switch (event.getType()) {
            case DEVICE_ADDED:
                kafkaProducer.send(new ProducerRecord<>(topic, null, eventTimestamp, hubId, HubMapperEvent.hubEventAvroAdded((DeviceAddedEvent) event)));
                break;
            case DEVICE_REMOVED:
                kafkaProducer.send(new ProducerRecord<>(topic, null, eventTimestamp, hubId, HubMapperEvent.hubEventAvroRemoved((DeviceRemovedEvent) event)));
                break;
            default:
                throw new IllegalArgumentException("Неизвестный тип события");
        }
    }

    @Override
    public <T extends SensorEvent> void collectSensorEvent(T event) {
        String hubId = event.getHubId();
        long eventTimestamp = event.getTimestamp().toEpochMilli();

        String topic = determineTopic(hubId);

        switch (event.getType()) {
            case CLIMATE_SENSOR_EVENT:
                kafkaProducer.send(new ProducerRecord<>(topic, null, eventTimestamp, hubId, SensorMapperEvent.climateSensorAvro((ClimateSensorEvent) event)));
                break;
            case LIGHT_SENSOR_EVENT:
                kafkaProducer.send(new ProducerRecord<>(topic, null, eventTimestamp, hubId, SensorMapperEvent.lightSensorAvro((LightSensorEvent) event)));
                break;
            case MOTION_SENSOR_EVENT:
                kafkaProducer.send(new ProducerRecord<>(topic, null, eventTimestamp, hubId, SensorMapperEvent.motionSensorAvro((MotionSensorEvent) event)));
                break;
            case SWITCH_SENSOR_EVENT:
                kafkaProducer.send(new ProducerRecord<>(topic, null, eventTimestamp, hubId, SensorMapperEvent.switchSensorAvro((SwitchSensorEvent) event)));
                break;
            case TEMPERATURE_SENSOR_EVENT:
                kafkaProducer.send(new ProducerRecord<>(topic, null, eventTimestamp, hubId, SensorMapperEvent.temperatureSensorAvro((TemperatureSensorEvent) event)));
                break;
            default:
                throw new IllegalArgumentException("Неизвестный тип события");
        }
    }

    private String determineTopic(String hubId) {
        // я пока отправляю по умолчанию, по тому-что не понимаю что отправлять какой топик или я вообще запутался
        // было бы круто если бы кинули тему где можно было бы изучить
        return hubTopic;
    }

    @Override
    public void close() throws Exception {
        kafkaProducer.flush();

        kafkaProducer.close();
    }
}
