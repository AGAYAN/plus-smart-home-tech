package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import ru.practicum.enums.TopicType;
import ru.practicum.event.hubs.DeviceAddedEvent;
import ru.practicum.event.hubs.DeviceRemovedEvent;
import ru.practicum.event.sensor.*;
import ru.practicum.mapper.HubMapperEvent;
import ru.practicum.mapper.SensorMapperEvent;
import ru.practicum.model.HubEvent;
import ru.practicum.model.SensorEvent;
import ru.practicum.serializer.config.ProducerConfig;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService, AutoCloseable {

    private final KafkaProducer<String, SpecificRecordBase> kafkaProducer;
    private final ProducerConfig producerConfig;


    @Override
    public <T extends HubEvent> void collectHubEvent(T event) {
        String hubId = event.getHubId();
        long eventTimestamp = event.getTimestamp().toEpochMilli();

        String topic = determineTopic(TopicType.HUB);
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

        String topic = determineTopic(TopicType.SENSOR);

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

    private String determineTopic(TopicType topicType) {
        return producerConfig.getTopic(topicType);
    }

    @Override
    public void close() throws Exception {
        kafkaProducer.flush();

        kafkaProducer.close();
    }
}
