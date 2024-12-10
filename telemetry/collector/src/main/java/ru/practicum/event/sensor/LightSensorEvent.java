package ru.practicum.event.sensor;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.enums.SensorEventType;
import ru.practicum.model.SensorEvent;

@Getter
@Setter
public class LightSensorEvent extends SensorEvent {

    private Integer linkQuality;

    private Integer luminosity;

    @Override
    public SensorEventType getType() {
        return SensorEventType.LIGHT_SENSOR_EVENT;
    }
}
