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

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    @Value("${topics.sensor}")
    private String sensorTopic;

    @Value("${topics.hubs}")
    private String hubTopic;

    final KafkaProducer<String, SpecificRecordBase> kafkaProducer;

    @Override
    public <T extends HubEvent> void collectHubEvent(T event) {
        if(event instanceof DeviceAddedEvent) {
            kafkaProducer.send(new ProducerRecord<>(hubTopic, HubMapperEvent.hubEventAvroAdded((DeviceAddedEvent) event)));
        } else if(event instanceof DeviceRemovedEvent) {
            kafkaProducer.send(new ProducerRecord<>(hubTopic, HubMapperEvent.hubEventAvroRemoved((DeviceRemovedEvent) event)));
        }
    }

    @Override
    public <T extends SensorEvent> void collectSensorEvent(T event) {
        if (event instanceof ClimateSensorEvent) {
            kafkaProducer.send(new ProducerRecord<>(sensorTopic, SensorMapperEvent.climateSensorAvro((ClimateSensorEvent) event)));
        } else if (event instanceof LightSensorEvent) {
            kafkaProducer.send(new ProducerRecord<>(sensorTopic, SensorMapperEvent.lightSensorAvro((LightSensorEvent) event)));
        } else if (event instanceof MotionSensorEvent) {
            kafkaProducer.send(new ProducerRecord<>(sensorTopic, SensorMapperEvent.motionSensorAvro((MotionSensorEvent) event)));
        } else if (event instanceof SwitchSensorEvent) {
            kafkaProducer.send(new ProducerRecord<>(sensorTopic, SensorMapperEvent.switchSensorAvro((SwitchSensorEvent) event)));
        } else if (event instanceof TemperatureSensorEvent) {
            kafkaProducer.send(new ProducerRecord<>(sensorTopic, SensorMapperEvent.temperatureSensorAvro((TemperatureSensorEvent) event)));
        }
    }
}
