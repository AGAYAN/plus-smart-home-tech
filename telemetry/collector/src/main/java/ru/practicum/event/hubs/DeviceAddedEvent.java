package ru.practicum.event.hubs;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.enums.DeviceType;
import ru.practicum.enums.HubEventType;
import ru.practicum.model.HubEvent;

@Getter
@Setter
public class DeviceAddedEvent extends HubEvent {

    private DeviceType deviceType;

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_ADDED;
    }
}
