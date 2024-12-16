package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.event.sensor.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

@UtilityClass
@Slf4j
public class SensorMapperEvent {

    public ClimateSensorAvro climateSensorAvro(ClimateSensorEvent climateSensorEvent) {
        return ClimateSensorAvro.newBuilder()
                .setCo2Level(climateSensorEvent.getCo2Level())
                .setHumidity(climateSensorEvent.getHumidity())
                .setTemperatureC(climateSensorEvent.getTemperatureC())
                .build();
    }

    public LightSensorAvro lightSensorAvro(LightSensorEvent lightSensorEvent) {
        return LightSensorAvro.newBuilder()
                .setLinkQuality(lightSensorEvent.getLinkQuality())
                .setLuminosity(lightSensorEvent.getLuminosity())
                .build();
    }

    public MotionSensorAvro motionSensorAvro(MotionSensorEvent motionSensorEvent) {
        return MotionSensorAvro.newBuilder()
                .setMotion(motionSensorEvent.isMotion())
                .setVoltage(motionSensorEvent.getVoltage())
                .setLinkQuality(motionSensorEvent.getLinkQuality())
                .build();
    }

    public SwitchSensorAvro switchSensorAvro(SwitchSensorEvent switchSensorEvent) {
        return SwitchSensorAvro.newBuilder()
                .setState(switchSensorEvent.isState())
                .build();
    }

    public TemperatureSensorAvro temperatureSensorAvro(TemperatureSensorEvent temperatureSensorEvent) {
        return TemperatureSensorAvro.newBuilder()
                .setId(temperatureSensorEvent.getId())
                .setHubId(temperatureSensorEvent.getHubId())
                .setTimestamp(temperatureSensorEvent.getTimestamp())
                .setTemperatureC(temperatureSensorEvent.getTemperatureC())
                .setTemperatureF(temperatureSensorEvent.getTemperatureF())
                .build();
    }
}
