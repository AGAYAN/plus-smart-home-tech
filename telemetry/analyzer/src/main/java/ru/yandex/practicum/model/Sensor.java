package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;

@Entity
@Getter
@Setter
@Table(name = "sensors")
@RequiredArgsConstructor
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String hubId;
    private DeviceTypeAvro type;

    public Sensor(String id, String hubId, DeviceTypeAvro type) {
        this.id = id;
        this.hubId = hubId;
        this.type = type;
    }
}