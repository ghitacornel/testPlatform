package platform.routes;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.feign.company.CompanyContract;
import platform.feign.company.CompanyDetailsResponse;
import platform.feign.product.ProductContract;
import platform.feign.product.ProductDetailsResponse;
import platform.feign.product.ProductSellRequest;

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
    private final CompanyContract companyContract;

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
                        .build())
                .process(exchange -> {
                    ProductSellRequest productSellRequest = exchange.getMessage().getBody(ProductSellRequest.class);
                    List<CompanyDetailsResponse> companyDetailsResponses = companyContract.findAll();
                    if (companyDetailsResponses.isEmpty()) {
                        throw new IllegalStateException("no companies available for creating products");
                    }
                    int index = random.nextInt(companyDetailsResponses.size());
                    productSellRequest.setCompanyId(companyDetailsResponses.get(index).getId());
                })
                .process(exchange -> productContract.sell(exchange.getMessage().getBody(ProductSellRequest.class)))
                .log("Create product ${body.id}")
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
                .log("Cancel product ${body.id}")
                .end();

    }

}

