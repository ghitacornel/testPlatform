package products.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@EnableJms
@Configuration
public class JMSConfigurationQueue {

    public static final String QueueProductCancellation = "QueueProductCancellation";

    @Bean
    Queue queueCompanyCancellation() {
        return new ActiveMQQueue(QueueProductCancellation);
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
    JmsTemplate jmsTemplateQueue(ConnectionFactory queueConnectionFactory) {
        return new JmsTemplate(queueConnectionFactory);
    }

}
