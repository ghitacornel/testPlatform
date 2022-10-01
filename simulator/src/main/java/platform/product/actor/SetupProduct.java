package platform.product.actor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import platform.common.AbstractActor;
import platform.product.model.Product;
import platform.product.model.ProductSale;
import platform.product.service.ProductService;
import platform.random.RandomDataCreatorService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
@DependsOn({"setupCompany"})
public class SetupProduct extends AbstractActor {

    private static final int MINIMUM_INITIAL_PRODUCTS_COUNT = 50;
    private final ProductService service;
    private final RandomDataCreatorService randomDataCreatorService;

    @PostConstruct
    public void setUp() {
        tearDown();
        Set<ProductSale> items = new HashSet<>();
        while (items.size() < MINIMUM_INITIAL_PRODUCTS_COUNT) {
            items.add(randomDataCreatorService.createProductSale());
        }
        for (ProductSale item : items) {
            Product registered = service.sale(item);
            log.info("selling " + registered);
        }
    }

    @PreDestroy
    public void tearDown() {
        for (Product item : service.findAll()) {
            log.info("cancelling " + item);
            service.cancel(item);
        }
    }
}
