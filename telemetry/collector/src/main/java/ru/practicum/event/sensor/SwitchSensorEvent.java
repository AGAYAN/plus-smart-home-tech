package ru.practicum.event.sensor;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.enums.SensorEventType;
import ru.practicum.model.SensorEvent;

@Setter
@Getter
public class SwitchSensorEvent extends SensorEvent {

    private boolean state;

    @Override
    public SensorEventType getType() {
        return SensorEventType.SWITCH_SENSOR_EVENT;
    }
}

