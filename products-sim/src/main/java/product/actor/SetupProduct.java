package product.actor;

import commons.model.IdResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import product.model.ProductSell;
import product.random.RandomDataCreatorService;
import product.service.ProductService;

@Component
@RequiredArgsConstructor
@Slf4j
public class SetupProduct {

    private static final int MINIMUM_INITIAL_PRODUCTS_COUNT = 50;

    private final ProductService productService;
    private final RandomDataCreatorService randomDataCreatorService;

    @PostConstruct
    public void setUp() {
        while (productService.countAll() < MINIMUM_INITIAL_PRODUCTS_COUNT) {
            ProductSell productSell = randomDataCreatorService.createProductSell();
            IdResponse response = productService.sell(productSell);
            log.info("selling " + response);
        }
    }

}
