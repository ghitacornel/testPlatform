package platform.product.actor;

import commons.model.IdResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import platform.common.AbstractActor;
import platform.product.model.ProductSale;
import platform.product.service.ProductService;
import platform.random.RandomDataCreatorService;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
@DependsOn({"setupCompany"})
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
