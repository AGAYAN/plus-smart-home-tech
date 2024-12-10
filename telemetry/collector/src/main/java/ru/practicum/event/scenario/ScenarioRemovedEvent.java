package ru.practicum.event.scenario;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.enums.HubEventType;
import ru.practicum.model.ScenarioEvent;

@Getter
@Setter
public class ScenarioRemovedEvent extends ScenarioEvent {

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_REMOVED;
    }
}
