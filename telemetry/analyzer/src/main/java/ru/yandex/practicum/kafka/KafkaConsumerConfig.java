package ru.yandex.practicum.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.Properties;

@Configuration
public class KafkaConsumerConfig {

    @Value("${bootstrap_server}")
    private static String bootstrapServer;

    @Value("${key_deserializer_class}")
    private static String keyDeserializerClass;

    @Value("${hub events_value_deserializer_class}")
    private static String hubEventsValueDeserializerClass;

    @Value("${snapshots_value_deserializer_class}")
    private static String snapshotValueDeserialize;

    @Bean
    public KafkaConsumer<String, HubEventAvro> hubEventsConsumer() {
        Properties consumerConfig = baseProperties();
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, hubEventsValueDeserializerClass);
        return new KafkaConsumer<>(consumerConfig);
    }

    @Bean
    public KafkaConsumer<String, SensorsSnapshotAvro> snapshotConsumer() {
        Properties consumerConfig = baseProperties();
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, snapshotValueDeserialize);
        return new KafkaConsumer<>(consumerConfig);
    }

    private static Properties baseProperties () {
        Properties config = new Properties();

        config.put(ConsumerConfig.GROUP_ID_CONFIG, "groupAnalyzerHubEventsConsumer");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializerClass);

        return config;
    }


}