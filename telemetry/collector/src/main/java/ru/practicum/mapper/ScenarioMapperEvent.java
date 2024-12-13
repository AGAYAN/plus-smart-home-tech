package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.event.scenario.DeviceAction;
import ru.practicum.event.scenario.ScenarioAddedEvent;
import ru.practicum.event.scenario.ScenarioCondition;
import ru.yandex.practicum.kafka.telemetry.event.*;

@Slf4j
public class ScenarioMapperEvent {

    public ScenarioConditionAvro scenarioConditionAvro(ScenarioCondition scenarioCondition) {
        return ScenarioConditionAvro.newBuilder()
                .setSensorId(scenarioCondition.getSensorId())
                .setValue(scenarioCondition.getValue())
                .setOperation(ConditionOperationAvro.valueOf(String.valueOf(scenarioCondition.getOperation())))
                .setType(ConditionTypeAvro.valueOf(String.valueOf(scenarioCondition.getType())))
                .build();
    }

    public DeviceActionAvro deviceActionAvro(DeviceAction deviceAction) {
        return DeviceActionAvro.newBuilder()
                .setSensorId(deviceAction.getSensorId())
                .setValue(deviceAction.getValue())
                .setType(ActionTypeAvro.valueOf(String.valueOf(deviceAction.getType())))
                .build();

    }

    public HubEventAvro hubEventAvro(ScenarioAddedEvent scenarioAddedEvent) {
        return HubEventAvro.newBuilder()
                .setHubId(scenarioAddedEvent.getHubId())
                .setTimestamp(scenarioAddedEvent.getTimestamp())
                .setPayload(scenarioAddedEvent.getConditions())
                .build();
    }
}
