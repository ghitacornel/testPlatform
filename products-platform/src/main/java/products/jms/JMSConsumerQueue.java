package products.jms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import products.config.JMSConfigurationQueue;
import products.repository.ProductRepository;
import products.repository.entity.Product;
import products.repository.entity.ProductStatus;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JMSConsumerQueue {

    private final ProductRepository repository;

    @JmsListener(destination = JMSConfigurationQueue.QueueCompanyCancellation)
    public void listenerForQueue1(Integer message) {
        List<Product> products = repository.findByCompanyId(message);
        for (Product product : products) {
            product.setStatus(ProductStatus.CANCELLED);
            log.info("cancelled product " + product);
        }
    }

}
