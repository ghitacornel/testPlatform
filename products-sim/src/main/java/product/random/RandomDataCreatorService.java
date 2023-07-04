package product.random;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import product.model.Product;
import product.model.ProductBuy;
import product.model.ProductSell;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class RandomDataCreatorService {

    private final Faker faker = Faker.instance();
    private final Random random = new Random();

    private final RandomDataFetchService randomDataFetchService;

    public ProductSell createProductSell() {

        ProductSell item = new ProductSell();
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
