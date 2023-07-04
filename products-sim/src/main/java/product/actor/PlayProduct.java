package product.actor;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import product.model.Product;
import product.model.ProductBuy;
import product.model.ProductSell;
import product.random.RandomDataCreatorService;
import product.random.RandomDataFetchService;
import product.service.ProductService;

@Service
@RequiredArgsConstructor
public class PlayProduct {

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
        ProductSell productSell = randomDataCreatorService.createProductSell();
        productService.sell(productSell);
    }

    @Scheduled(fixedRate = 2000, initialDelay = 1000)
    public void operateCancel() {
        if (productService.countAll() < MINIMUM) {
            return;
        }
        Product product = randomDataFetchService.findRandomProduct();
        productService.cancel(product);
    }

    @Scheduled(fixedRate = 25, initialDelay = 2000)
    public void operateBuy() {
        ProductBuy productBuy = randomDataCreatorService.createProductBuy();
        productService.buy(productBuy);
    }

}
