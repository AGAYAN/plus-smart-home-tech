package ru.yandex.practicum.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.deserializer.SnapshotService;
import ru.yandex.practicum.grpc.telemetry.event.*;
import ru.yandex.practicum.kafka.KafkaConsumerConfig;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Duration;
import java.util.Collections;

@Component
@Slf4j
public class SnapshotProcessor implements Runnable {

    private final KafkaConsumer<String, SensorsSnapshotAvro> consumer;
    private final SnapshotService snapshotService;

    public SnapshotProcessor(KafkaConsumerConfig kafkaConsumerConfig, SnapshotService snapshotService) {
        this.consumer = kafkaConsumerConfig.snapshotConsumer();
        this.snapshotService = snapshotService;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Остановка SnapshotProcessor");
            consumer.wakeup(); // Прерывание работы консюмера
        }));
    }


    @Override
    public void run() {
        try {
            consumer.subscribe(Collections.singleton("telemetry.hubs.v1"));

            while (!Thread.currentThread().isInterrupted()) {
                ConsumerRecords<String, SensorsSnapshotAvro> records = consumer.poll(Duration.ofMillis(100));

                // Обработка каждой записи
                for (ConsumerRecord<String, SensorsSnapshotAvro> record : records) {
                    snapshotService.handleSnapshot(record.value());
                    processSnapshot(record.value());
                }
            }
        } catch (WakeupException e) {
            log.info("Получен сигнал на остановку консюмера.");
        } catch (Exception e) {
            log.error("Ошибка при обработке событий хабов: ", e);
        } finally {
            consumer.close();
        }
    }

    private void processSnapshot(SensorsSnapshotAvro snapshot) {
        log.info("Обработка снапшота для хаба: {}", snapshot.getHubId());

        switch (snapshot.get(snapshot.getHubId())) {
            case MotionSensorEvent mse -> processMotionSensor(snapshot.getHubId(), mse);
            case TemperatureSensorEvent tse -> processTemperatureSensor(snapshot.getHubId(), tse);
            case LightSensorEvent lse -> processLightSensor(snapshot.getHubId(), lse);
            case ClimateSensorEvent cse -> processClimateSensor(snapshot.getHubId(), cse);
            case SwitchSensorEvent sse -> processSwitchSensor(snapshot.getHubId(), sse);
            default -> log.warn("Получено событие неизвестного типа: {}", snapshot);
        }
    }

    private void processMotionSensor(String hubId, MotionSensorEvent event) {
        log.info("Motion Sensor [hubId={}]: Motion={}, Link Quality={}, Voltage={}",
                hubId, event.getMotion(), event.getLinkQuality(), event.getVoltage());

        // Логика обработки движения
        if (event.getMotion()) {
            log.info("Движение обнаружено на хабе {}: Включаем свет.", hubId);
            log.info("Включаем свет для хаба {}", hubId);
        } else {
            log.info("Движение не обнаружено на хабе {}: Выключаем свет.", hubId);
            log.info("Выключаем свет для хаба {}", hubId);
        }
        // Логика обработки событий движения
    }

    private void processTemperatureSensor(String hubId, TemperatureSensorEvent event) {
        log.info("Temperature Sensor [hubId={}]: Temp (C)={}, Temp (F)={}",
                hubId, event.getTemperatureC(), event.getTemperatureF());

        if (event.getTemperatureC() < 15) {
            log.info("Температура ниже 15°C на хабе {}: Включаем обогреватель.", hubId);
            log.info("Включаем обогреватель для хаба {}", hubId);
        } else if (event.getTemperatureC() > 25) {
            log.info("Температура выше 25°C на хабе {}: Включаем кондиционер.", hubId);
            log.info("Включаем кондиционер для хаба {}", hubId);
        }
        // Логика обработки температурных данных
    }

    private void processLightSensor(String hubId, LightSensorEvent event) {
        log.info("Light Sensor [hubId={}]: Luminosity={}, Link Quality={}",
                hubId, event.getLuminosity(), event.getLinkQuality());
        if (event.getLuminosity() < 50) {
            log.info("Низкая освещённость на хабе {}: Включаем свет.", hubId);
            log.info("Включаем осушитель для хаба {}", hubId);
        } else {
            log.info("Достаточная освещённость на хабе {}: Выключаем свет.", hubId);
            log.info("Включаем вентиляцию для хаба {}", hubId);
        }
        // Логика обработки световых данных
    }

    private void processClimateSensor(String hubId, ClimateSensorEvent event) {
        log.info("Climate Sensor [hubId={}]: Temp (C)={}, Humidity={}, CO2 Level={}",
                hubId, event.getTemperatureC(), event.getHumidity(), event.getCo2Level());

        if (event.getTemperatureC() > 30) {
            log.info("Высокая температура на хабе {}: Включаем кондиционер.", hubId);
            log.info("Включаем кондиционер для хаба {}", hubId);
        }

        if (event.getHumidity() > 70) {
            log.info("Высокая влажность на хабе {}: Включаем осушитель воздуха.", hubId);
            log.info("Включаем осушитель для хаба {}", hubId);
        }

        if (event.getCo2Level() > 1000) {
            log.info("Высокий уровень CO2 на хабе {}: Включаем вентиляцию.", hubId);
            log.info("Включаем вентиляцию для хаба {}", hubId);
        }
        // Логика обработки климатических данных
    }

    private void processSwitchSensor(String hubId, SwitchSensorEvent event) {
        log.info("Switch Sensor [hubId={}]: State={}", hubId, event.getState());

        if ("ON".equalsIgnoreCase(String.valueOf(event.getState()))) {
            log.info("Переключатель ВКЛ на хабе {}: Активируем соответствующее устройство.", hubId);
            log.info("Активируем устройство для хаба {}", hubId);
        } else if ("OFF".equalsIgnoreCase(String.valueOf(event.getState()))) {
            log.info("Переключатель ВЫКЛ на хабе {}: Деактивируем соответствующее устройство.", hubId);
            log.info("Деактивируем устройство для хаба {}", hubId);
        }
        // Логика обработки данных переключателя
    }

    public void start() {
        run();
    }
}
