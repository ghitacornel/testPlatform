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

    private static final int MINIMUM = 50;
    private static final int MAXIMUM = 200;
    private final ProductService productService;
    private final RandomDataFetchService randomDataFetchService;
    private final RandomDataCreatorService randomDataCreatorService;

    @Scheduled(fixedRate = 100, initialDelay = 1000)
    public void operateSale() {
        if (productService.countAll() > MAXIMUM) {
            return;
        }
        ProductSale productSale = randomDataCreatorService.createProductSale();
        log.debug("trying to sell product " + productSale);
        productService.sale(productSale);
    }

    @Scheduled(fixedRate = 2000, initialDelay = 1000)
    public void operateCancel() {
        if (productService.countAll() < MINIMUM) {
            return;
        }
        Product product = randomDataFetchService.findRandomProduct();
        log.debug("trying to cancel product " + product);
        productService.cancel(product);
    }

    @Scheduled(fixedRate = 100, initialDelay = 2000)
    public void operateBuy() {
        ProductBuy productBuy = randomDataCreatorService.createProductBuy();
        log.debug("trying to buy product " + productBuy);
        productService.buy(productBuy);
    }

}
