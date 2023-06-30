package product.actor;

import commons.model.IdResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import product.common.AbstractActor;
import product.model.ProductSale;
import product.random.RandomDataCreatorService;
import product.service.ProductService;

@Component
@RequiredArgsConstructor
@Slf4j
public class SetupProduct extends AbstractActor {

    private static final int MINIMUM_INITIAL_PRODUCTS_COUNT = 50;

    private final ProductService productService;
    private final RandomDataCreatorService randomDataCreatorService;

    @PostConstruct
    public void setUp() {

        while (productService.countAll() < MINIMUM_INITIAL_PRODUCTS_COUNT) {
            ProductSale productSale = randomDataCreatorService.createProductSale();
            IdResponse response = productService.sale(productSale);
            log.info("selling " + response);
        }
    }

}
