package ru.practicum.event.sensor;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.enums.SensorEventType;
import ru.practicum.model.SensorEvent;

@Getter
@Setter
public class ClimateSensorEvent extends SensorEvent {
    private Integer temperatureC;

    private Integer humidity;

    private Integer co2Level;

    private SensorEventType type;

    @Override
    public SensorEventType getType() {
        return SensorEventType.CLIMATE_SENSOR_EVENT;
    }
}
