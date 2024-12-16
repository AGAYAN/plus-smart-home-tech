package ru.practicum.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.enums.HubEventType;
import ru.practicum.event.hubs.DeviceAddedEvent;
import ru.practicum.event.hubs.DeviceRemovedEvent;

import java.time.Instant;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = HubEventType.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DeviceAddedEvent.class, name = "DEVICE_ADDED"),
        @JsonSubTypes.Type(value = DeviceRemovedEvent.class, name = "DEVICE_REMOVED")
})
@Getter
@Setter
public abstract class HubEvent {
    private String id;
    private String hubId;
    Instant timestamp = Instant.now();

    public abstract HubEventType getType();
}
