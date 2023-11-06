package flows.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Queue;
import jakarta.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
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
    JmsComponent jmsComponent(ActiveMQConnectionFactory connectionFactory) {
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(connectionFactory);
        return jmsComponent;
    }

    @Bean
    MessageConverter messageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Bean
    DynamicDestinationResolver destinationResolver() {
        return new DynamicDestinationResolver() {
            @Override
            public Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain) throws JMSException {
                pubSubDomain = destinationName.endsWith("Topic");
                return super.resolveDestinationName(session, destinationName, pubSubDomain);
            }
        };
    }

    @Bean
    JmsTemplate jmsTemplate(ActiveMQConnectionFactory connectionFactory, MessageConverter messageConverter, DestinationResolver destinationResolver) {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setMessageConverter(messageConverter);
        template.setPubSubDomain(true);
        template.setDestinationResolver(destinationResolver);
        template.setDeliveryPersistent(true);
        return template;
    }

    @Bean
    Queue completedOrdersQueue() {
        return new ActiveMQQueue("CompletedOrdersQueueName");
    }

}
