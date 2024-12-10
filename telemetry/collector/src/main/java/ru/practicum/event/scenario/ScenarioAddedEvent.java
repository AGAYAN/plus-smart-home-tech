package ru.practicum.event.scenario;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.enums.HubEventType;
import ru.practicum.model.ScenarioEvent;

import java.util.List;

@Getter
@Setter
public class ScenarioAddedEvent extends ScenarioEvent {

    private List<ScenarioCondition> conditions;

    private List<DeviceAction> actions;

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED;
    }
}
