package ru.practicum.event.hubs;

import ru.practicum.enums.HubEventType;
import ru.practicum.model.HubEvent;

public class DeviceRemovedEvent extends HubEvent {

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_REMOVED;
    }
}
