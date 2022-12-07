package platform.product.actor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import platform.common.AbstractActor;
import platform.product.model.Product;
import platform.product.model.ProductBuy;
import platform.product.model.ProductSale;
import platform.product.service.ProductService;
import platform.random.RandomDataCreatorService;
import platform.random.RandomDataFetchService;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayProduct extends AbstractActor {

    private static final int MINIMUM_PRODUCTS_COUNT = 50;
    private static final int MAXIMUM_PRODUCTS_COUNT = 200;
    private final ProductService productService;
    private final RandomDataFetchService randomDataFetchService;
    private final RandomDataCreatorService randomDataCreatorService;

    @Scheduled(fixedRate = 100, initialDelay = 1000)
    public void operateSale() {
        if (productService.countAll() > MAXIMUM_PRODUCTS_COUNT) {
            return;
        }
        ProductSale productSale = randomDataCreatorService.createProductSale();
        if (productSale == null) {
            log.warn("no product to sell");
            return;
        }
        log.info("trying to sell product " + productSale);
        productService.sale(productSale);
    }

    @Scheduled(fixedRate = 2000, initialDelay = 1000)
    public void operateCancel() {
        if (productService.countAll() <= MINIMUM_PRODUCTS_COUNT) {
            return;
        }
        Product product = randomDataFetchService.findRandomProduct();
        if (product == null) {
            log.warn("no product to cancel");
            return;
        }
        log.info("trying to cancel product " + product);
        productService.cancel(product);
    }

    @Scheduled(fixedRate = 100, initialDelay = 2000)
    public void operateBuy() {
        ProductBuy productBuy = randomDataCreatorService.createProductBuy();
        if (productBuy == null) {
            log.warn("no product to buy");
            return;
        }
        log.info("trying to buy product " + productBuy);
        productService.buy(productBuy);
    }

}
