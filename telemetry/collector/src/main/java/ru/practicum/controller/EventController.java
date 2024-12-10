package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.HubEvent;
import ru.practicum.model.SensorEvent;
import ru.practicum.service.EventService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    @PostMapping("/hubs")
    public void collectHubsEvent(@RequestBody HubEvent hubEvent) {
        eventService.collectHubEvent(hubEvent);
    }

    @PostMapping("/sensors")
    public void collectSensorEvent(@RequestBody SensorEvent event) {
        eventService.collectSensorEvent(event);
    }
}
