package ru.practicum.event.sensor;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.enums.SensorEventType;
import ru.practicum.model.SensorEvent;

@Getter
@Setter
public class MotionSensorEvent extends SensorEvent {

    private Integer linkQuality;

    private boolean motion;

    private Integer voltage;

    @Override
    public SensorEventType getType() {
        return SensorEventType.MOTION_SENSOR_EVENT;
    }
}
