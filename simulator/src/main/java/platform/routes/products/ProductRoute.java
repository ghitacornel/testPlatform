package platform.routes.products;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.routes.products.feign.ProductBuyRequest;
import platform.routes.products.feign.ProductContract;
import platform.routes.products.feign.ProductDetailsResponse;
import platform.routes.products.feign.ProductSellRequest;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class ProductRoute extends RouteBuilder {

    private static final int MINIMUM = 50;
    private static final int MAXIMUM = 2000;

    private final Random random = new Random();
    private final Faker faker = Faker.instance();

    private final ProductContract productContract;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=50&delay=1000")
                .routeId("sale-product-route")
                .setBody(exchange -> productContract.countAllActive())
                .filter(body().isLessThan(ProductRoute.MAXIMUM))
                .setBody(exchange -> ProductSellRequest.builder()
                        .name(faker.commerce().productName())
                        .color(faker.commerce().color())
                        .price(Double.valueOf(faker.commerce().price(1, 100)))
                        .quantity(random.nextInt(10000) + 100)
                        .companyId(-1)// TODO
                        .build())
                .process(exchange -> productContract.sell(exchange.getMessage().getBody(ProductSellRequest.class)))
                .log("${body}")
                .end();

        from("timer://simpleTimer?period=25&delay=2000")
                .routeId("buy-product-route")
                .setBody(exchange -> productContract.findAllActive())
                .setBody(exchange -> {
                    List<ProductDetailsResponse> data = exchange.getMessage().getBody(List.class);
                    int index = random.nextInt(data.size());
                    return data.get(index);
                })
                .setBody(exchange -> {
                    ProductDetailsResponse productDetailsResponse = exchange.getMessage().getBody(ProductDetailsResponse.class);
                    return ProductBuyRequest.builder()
                            .clientId(-1)// TODO
                            .productId(productDetailsResponse.getId())
                            .quantity(generateRandomQuantity(productDetailsResponse))
                            .build();
                })
                .process(exchange -> productContract.buy(exchange.getMessage().getBody(ProductBuyRequest.class)))
                .log("${body}")
                .end();

        from("timer://simpleTimer?period=2000&delay=1000")
                .routeId("cancel-product-route")
                .setBody(exchange -> productContract.countAllActive())
                .filter(body().isGreaterThan(ProductRoute.MINIMUM))
                .setBody(exchange -> productContract.findAllActive())
                .setBody(exchange -> {
                    List<ProductDetailsResponse> data = exchange.getMessage().getBody(List.class);
                    int index = random.nextInt(data.size());
                    return data.get(index);
                })
                .process(exchange -> productContract.cancel(exchange.getMessage().getBody(ProductDetailsResponse.class).getId()))
                .log("Cancel product : ${body}")
                .end();

    }

    private int generateRandomQuantity(ProductDetailsResponse productDetailsResponse) {
        int quantity = random.nextInt(productDetailsResponse.getQuantity());
        if (quantity == 0) quantity++;
        return quantity;
    }

}

