package companies.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Queue;

@EnableJms
@Configuration
public class JMSConfigurationQueue {

    private static final String QueueProductCancellation = "QueueProductCancellation";

    @Bean
    Queue queueProductCancellation() {
        return new ActiveMQQueue(QueueProductCancellation);
    }

    @Bean
    JmsListenerContainerFactory<?> queueConnectionFactory(ConnectionFactory connectionFactory,
                                                          DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    JmsTemplate jmsTemplateQueue(ConnectionFactory queueConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(queueConnectionFactory);
        jmsTemplate.setSessionTransacted(true);
        return jmsTemplate;
    }

}
