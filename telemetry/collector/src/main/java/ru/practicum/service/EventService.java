package ru.practicum.service;

import ru.practicum.model.HubEvent;
import ru.practicum.model.SensorEvent;

public interface EventService {
    <T extends HubEvent> void collectHubEvent(T event);

    <T extends SensorEvent> void collectSensorEvent(T event);
}
