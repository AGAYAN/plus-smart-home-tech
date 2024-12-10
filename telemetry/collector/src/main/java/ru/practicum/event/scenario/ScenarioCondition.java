package ru.practicum.event.scenario;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.enums.ConditionType;
import ru.practicum.enums.OperationsType;

@Setter
@Getter
public class ScenarioCondition {

    private String sensorId;

    private ConditionType type;

    private OperationsType operation;

    private Integer value;
}
