package ru.yandex.practicum.deserializer;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SnapshotService {

    private final Map<String, SensorsSnapshotAvro> snapshots = new ConcurrentHashMap<>();

    public Optional<SensorsSnapshotAvro> updateState(SensorEventAvro event) {
        // Получаем идентификатор хаба
        String hubId = event.getHubId();

        // Если для хаба ещё нет снимка состояния, создаём новый с пустым набором состояний
        snapshots.putIfAbsent(hubId, new SensorsSnapshotAvro(hubId, event.getTimestamp(), new HashMap<>()));
        SensorsSnapshotAvro snapshot = snapshots.get(hubId);

        // Получаем текущее состояние датчика по его ID
        SensorStateAvro currentState = snapshot.getSensorsState().get(event.getId());
        //Сравнение
        if (currentState != null && currentState.getData().equals(event.getPayload())) {
            return Optional.empty();
        }

        SensorStateAvro newState = new SensorStateAvro(event.getTimestamp(), event.getPayload());

        // обновляем данные
        snapshot.getSensorsState().put(event.getId(), newState);
        snapshot.setTimestamp(event.getTimestamp());
        return Optional.of(snapshot);
    }

    public void handleSnapshot(SensorsSnapshotAvro snapshot) {
        // Логика обработки снапшота
        snapshots.put(snapshot.getHubId(), snapshot);
        System.out.println("Сохранён снапшот для хаба: " + snapshot.getHubId());
    }
}