package ru.practicum.event.scenario;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.enums.DeviceActionType;

@Getter
@Setter
public class DeviceAction {

    private String sensorId;

    private DeviceActionType type;

    private Integer value;
}
