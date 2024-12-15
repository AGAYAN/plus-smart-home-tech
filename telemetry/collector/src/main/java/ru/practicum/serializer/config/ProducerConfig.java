package ru.practicum.serializer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import ru.practicum.enums.TopicType;

import java.util.EnumMap;
import java.util.Map;

@Configuration
public class ProducerConfig {

    private final Map<TopicType, String> topics = new EnumMap<>(TopicType.class);

    public ProducerConfig(
            @Value("${topics.sensor}") String sensorTopic,
            @Value("${topics.hubs}") String hubTopic
    ) {
        topics.put(TopicType.SENSOR, sensorTopic);
        topics.put(TopicType.HUB, hubTopic);
    }

    public String getTopic(TopicType topicType) {
        return topics.get(topicType);
    }
    //Надеюсь правельно сделал а то последняя попытка
}
