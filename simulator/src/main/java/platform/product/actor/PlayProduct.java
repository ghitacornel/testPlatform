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
import platform.random.RandomDataCreator;
import platform.random.RandomDataFetch;

//@Service
@RequiredArgsConstructor
@Slf4j
public class PlayProduct extends AbstractActor {

    private final ProductService productService;
    private final RandomDataFetch randomDataFetch;
    private final RandomDataCreator randomDataCreator;

    @Scheduled(fixedRate = 100, initialDelay = 2000)
    public void operateSale() {
        if (productService.countAll() > 200) return;
        ProductSale productSale = randomDataCreator.createProductSale();
        if (productSale == null) {
            log.warn("no product to sell");
            return;
        }
        productService.sale(productSale);
    }

    @Scheduled(fixedRate = 1000, initialDelay = 2000)
    public void operateCancel() {
        if (productService.countAll() <= 50) return;
        Product product = randomDataFetch.findRandomProduct();
        if (product == null) {
            log.warn("no product to cancel");
            return;
        }
        productService.cancel(product);
    }

    @Scheduled(fixedRate = 100, initialDelay = 2000)
    public void operateBuy() {
        ProductBuy productBuy = randomDataCreator.createProductBuy();
        if (productBuy == null) {
            log.warn("no product to buy");
            return;
        }
        productService.buy(productBuy);
    }

}
