package platform.fakers;

import contracts.products.ProductSellRequest;
import net.datafaker.Faker;

import java.util.Random;

public class ProductSellRequestFaker {

    private static final Random random = new Random();
    private static final Faker faker = new Faker();

    public static ProductSellRequest fake() {
        return ProductSellRequest.builder()
                .name(faker.commerce().productName())
                .color(faker.commerce().color())
                .price(Double.valueOf(faker.commerce().price(1, 100)))
                .quantity(random.nextInt(100000) + 100)
                .build();
    }

}
