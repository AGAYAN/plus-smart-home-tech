package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.event.hubs.DeviceAddedEvent;
import ru.practicum.event.hubs.DeviceRemovedEvent;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@UtilityClass
@Slf4j
public class HubMapperEvent {

    public static HubEventAvro hubEventAvroAdded(DeviceAddedEvent deviceAddedEvent) {
        return HubEventAvro.newBuilder()
                .setHubId(deviceAddedEvent.getHubId())
                .setTimestamp(deviceAddedEvent.getTimestamp())
                .setPayload(DeviceAddedEventAvro.newBuilder()
                        .setId(deviceAddedEvent.getId())
                        .setType(DeviceTypeAvro.valueOf(deviceAddedEvent.getDeviceType().toString())))
                .build();

    }

    public static HubEventAvro hubEventAvroRemoved(DeviceRemovedEvent deviceRemovedEvent) {
        return HubEventAvro.newBuilder()
                .setHubId(deviceRemovedEvent.getHubId())
                .setTimestamp(deviceRemovedEvent.getTimestamp())
                .setPayload(DeviceRemovedEventAvro.newBuilder()
                        .setId(deviceRemovedEvent.getId())
                        .build()).build();
    }
}
