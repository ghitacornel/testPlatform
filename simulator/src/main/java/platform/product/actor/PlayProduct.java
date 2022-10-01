package platform.product.actor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import platform.common.AbstractActor;
import platform.product.model.Product;
import platform.product.model.ProductBuy;
import platform.product.model.ProductSale;
import platform.product.service.ProductService;
import platform.random.RandomDataCreatorService;
import platform.random.RandomDataFetchService;

//@Service
@RequiredArgsConstructor
@Slf4j
public class PlayProduct extends AbstractActor {

    private final ProductService productService;
    private final RandomDataFetchService randomDataFetchService;
    private final RandomDataCreatorService randomDataCreatorService;

    @Scheduled(fixedRate = 100, initialDelay = 2000)
    public void operateSale() {
        if (productService.countAll() > 200) return;
        ProductSale productSale = randomDataCreatorService.createProductSale();
        if (productSale == null) {
            log.warn("no product to sell");
            return;
        }
        productService.sale(productSale);
    }

    @Scheduled(fixedRate = 1000, initialDelay = 2000)
    public void operateCancel() {
        if (productService.countAll() <= 50) return;
        Product product = randomDataFetchService.findRandomProduct();
        if (product == null) {
            log.warn("no product to cancel");
            return;
        }
        productService.cancel(product);
    }

    @Scheduled(fixedRate = 100, initialDelay = 2000)
    public void operateBuy() {
        ProductBuy productBuy = randomDataCreatorService.createProductBuy();
        if (productBuy == null) {
            log.warn("no product to buy");
            return;
        }
        productService.buy(productBuy);
    }

}
