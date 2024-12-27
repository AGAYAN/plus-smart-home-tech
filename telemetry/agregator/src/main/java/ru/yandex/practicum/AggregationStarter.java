package ru.yandex.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.deserializer.SnapshotService;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Duration;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class AggregationStarter {

    @Value("${topics.sensors}")
    private String sensorsTopic;

    @Value("${bootstrap_server}")
    private String bootstrapServer;

    @Value("${key_deserializer_class}")
    private String keyDeserializerClass;

    @Value("${value_deserializer_class}")
    private String valueDeserializerClass;

    @Value("${key_serializer_class}")
    private String keySerializerClass;

    @Value("${value_serializer_class}")
    private String valueSerializerClass;

    @Value("${topics.snapshots}")
    private String sensorsSnapshotsTopic;

    private SnapshotService service;

    Map<String, SensorsSnapshotAvro> snapshots = new HashMap<>();

    public void start() {

        Producer<String, SpecificRecordBase> producer;

        Properties producerConfig = new Properties();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializerClass);
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializerClass);

        producer = new KafkaProducer<>(producerConfig);

        Properties consumerConfig = new Properties();

        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializerClass);
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializerClass);
        consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        Consumer<String, SensorEventAvro> consumer = new KafkaConsumer<>(consumerConfig);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Сработал хук на завершение JVM. Прерываю работу консьюмера.");
            consumer.wakeup();
        }));

        try {
            consumer.subscribe(List.of(sensorsTopic));
            while (true) {
                ConsumerRecords<String, SensorEventAvro> records =
                        consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, SensorEventAvro> record : records) {
                    try {
                        log.info("Получено сообщение из партиции {}, со смещением {}:\n{}\n",
                                record.partition(), record.offset(), record.value());
                        Optional<SensorsSnapshotAvro> optionalSensorsSnapshotAvro = service.updateState(record.value());
                        optionalSensorsSnapshotAvro
                                .ifPresent(sensorsSnapshotAvro -> {
                                    snapshots
                                            .put(record.value().getHubId(), sensorsSnapshotAvro);

                                    producer.send(new ProducerRecord<>(sensorsSnapshotsTopic, sensorsSnapshotAvro));

                                    log.info("{} отправлено {}", this.getClass().getName(), sensorsSnapshotAvro);
                                });
                    } catch (Exception e) {
                        log.error("Ошибка обработка сообщения: {}", record.value(), e);
                    }
                }
                try{
                    consumer.commitSync();
                    log.info("Оффсеты зафиксирован");
                } catch (CommitFailedException e) {
                    log.error("Ошибки фиксации оффсетов", e);
                }
            }

        } catch (Exception e) {
            log.error("Ошибка во время обработки событий от датчиков", e);
        } finally {
            consumer.close();
            producer.close();
        }
    }

}
