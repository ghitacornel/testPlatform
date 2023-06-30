package product.random;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import product.common.AbstractActor;
import product.model.Product;
import product.model.ProductBuy;
import product.model.ProductSale;

@Service
@RequiredArgsConstructor
@Slf4j
public class RandomDataCreatorService extends AbstractActor {

    private final RandomDataFetchService randomDataFetchService;

    public ProductSale createProductSale() {

        ProductSale item = new ProductSale();
        item.setName(faker.commerce().productName());
        item.setColor(faker.commerce().color());
        item.setPrice(Double.valueOf(faker.commerce().price(1, 100)));
        item.setQuantity(random.nextInt(10000) + 100);
        item.setCompanyId(-1);// TODO
        return item;
    }

    public ProductBuy createProductBuy() {

        Product product = randomDataFetchService.findRandomProduct();
        if (product == null) return null;

        ProductBuy item = new ProductBuy();
        item.setClientId(-1);// TODO
        item.setProductId(product.getId());
        int quantity = random.nextInt(product.getQuantity());
        if (quantity == 0) quantity++;
        item.setQuantity(quantity);
        return item;
    }

}
