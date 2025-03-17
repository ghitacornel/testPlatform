package flows.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Queue;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.beans.factory.annotation.Value;

@EnableJms
@Configuration
class JMSConfiguration {

    @Value("${spring.activemq.broker-url}")
    private String BROKER_URL;
    @Value("${spring.activemq.user}")
    private String BROKER_USERNAME;
    @Value("${spring.activemq.password}")
    private String BROKER_PASSWORD;

    @Bean
    ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_USERNAME, BROKER_PASSWORD, BROKER_URL);
        connectionFactory.setTrustAllPackages(true);
        return connectionFactory;
    }

    @Bean
    MessageConverter messageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Bean
    JmsListenerContainerFactory<?> queueConnectionFactory(ConnectionFactory connectionFactory,
                                                          DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some of Boot's default if necessary.
        return factory;
    }

    @Bean
    JmsTemplate jmsTemplateQueue(ConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
    }

    @Bean
    Queue completedOrdersQueue() {
        return new ActiveMQQueue("CompletedOrdersQueueName");
    }

}
