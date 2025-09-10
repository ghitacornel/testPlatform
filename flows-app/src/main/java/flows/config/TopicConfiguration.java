package flows.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TopicConfiguration {

    @Value(value = "${kafka.topic.completedOrders}")
    private String completedOrdersTopic;

    @Value(value = "${kafka.topic.toBeConfirmedOrders}")
    private String toBeConfirmedOrdersTopic;

    @Bean
    NewTopic completedOrdersTopic() {
        return new NewTopic(completedOrdersTopic, 1, (short) 1);
    }

    @Bean
    NewTopic toBeConfirmedOrdersTopic() {
        return new NewTopic(toBeConfirmedOrdersTopic, 1, (short) 1);
    }
}