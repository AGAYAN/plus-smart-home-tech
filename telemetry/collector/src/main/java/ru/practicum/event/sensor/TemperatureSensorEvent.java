package ru.practicum.event.sensor;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.enums.SensorEventType;
import ru.practicum.model.SensorEvent;

@Getter
@Setter
public class TemperatureSensorEvent extends SensorEvent {

    private Integer temperatureC;

    private Integer temperatureF;

    @Override
    public SensorEventType getType() {
        return SensorEventType.TEMPERATURE_SENSOR_EVENT;
    }
}
