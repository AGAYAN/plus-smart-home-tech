package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "scenarios")
public class Scenarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hub_id;
    private String name;

    public Scenarios(String hubId, String name) {
        this.hub_id = hubId;
        this.name = name;
    }
}
