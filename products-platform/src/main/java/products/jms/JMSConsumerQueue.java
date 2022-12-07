package products.jms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import products.config.JMSConfigurationQueue;
import products.repository.ProductRepository;

import javax.transaction.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JMSConsumerQueue {

    private final ProductRepository repository;

    @JmsListener(destination = JMSConfigurationQueue.QueueProductCancellation)
    @Transactional
    public void listenerForQueue1(Integer id) {
        repository.cancelByCompanyId(id);
        log.info("all products cancelled fro company id " + id);
    }

}
