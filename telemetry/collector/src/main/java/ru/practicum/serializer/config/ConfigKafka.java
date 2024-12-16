package ru.practicum.serializer.config;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Properties;

@Configuration
public class ConfigKafka {

    @Value("${kafka.server}")
    private String server;

    @Value("${kafka.key.serialize.class}")
    private String keySerializeClass;

    @Value("${kafka.value.serialize.class}")
    private String valueSerializeClass;


    @Bean
    KafkaProducer<String, SpecificRecordBase> producer() {
        Properties property = new Properties();
        property.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        property.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializeClass);
        property.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializeClass);

        return new KafkaProducer<>(property);
    }
}