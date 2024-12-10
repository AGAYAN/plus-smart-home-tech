package ru.practicum.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.enums.HubEventType;
import ru.practicum.enums.SensorEventType;
import ru.practicum.event.scenario.ScenarioAddedEvent;
import ru.practicum.event.scenario.ScenarioRemovedEvent;
import ru.practicum.event.sensor.*;

import java.time.Instant;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = SensorEventType.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ScenarioAddedEvent.class, name = "SCENARIO_ADDED"),
        @JsonSubTypes.Type(value = ScenarioRemovedEvent.class, name = "SCENARIO_REMOVED"),
})
@Getter
@Setter
public abstract class ScenarioEvent {

    private String hubId;

    private Instant timestamp = Instant.now();

    private String name;

    public abstract HubEventType getType();
}
