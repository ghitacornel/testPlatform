package product.actor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import product.common.AbstractActor;
import product.model.Product;
import product.model.ProductBuy;
import product.model.ProductSale;
import product.random.RandomDataCreatorService;
import product.random.RandomDataFetchService;
import product.service.ProductService;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayProduct extends AbstractActor {

    private static final int MINIMUM = 50;
    private static final int MAXIMUM = 2000;

    private final ProductService productService;
    private final RandomDataFetchService randomDataFetchService;
    private final RandomDataCreatorService randomDataCreatorService;

    @Scheduled(fixedRate = 50, initialDelay = 1000)
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

    @Scheduled(fixedRate = 25, initialDelay = 2000)
    public void operateBuy() {
        ProductBuy productBuy = randomDataCreatorService.createProductBuy();
        log.debug("trying to buy product " + productBuy);
        productService.buy(productBuy);
    }

}