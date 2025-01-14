package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.Scenarios;

import java.util.List;
import java.util.Optional;

public interface ScenarioRepository extends JpaRepository<Scenarios, Long> {
    List<Scenarios> findByHubId(String hubId);
    Optional<Scenarios> findByHubIdAndName(String hubId, String name);
}