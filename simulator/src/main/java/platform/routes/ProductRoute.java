package platform.routes;

import com.github.javafaker.Faker;
import contracts.companies.CompanyDetailsResponse;
import contracts.products.ProductDetailsResponse;
import contracts.products.ProductSellRequest;
import lombok.RequiredArgsConstructor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.clients.CompanyClient;
import platform.clients.ProductClient;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class ProductRoute extends RouteBuilder {

    private static final int MINIMUM = 50;
    private static final int MAXIMUM = 2000;

    private final Random random = new Random();
    private final Faker faker = Faker.instance();

    private final ProductClient productClient;
    private final CompanyClient companyClient;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=1000&delay=1000")
                .routeId("sale-product-route")
                .setBody(exchange -> productClient.countAllActive())
                .filter(body().isLessThan(ProductRoute.MAXIMUM))
                .setBody(exchange -> ProductSellRequest.builder()
                        .name(faker.commerce().productName())
                        .color(faker.commerce().color())
                        .price(Double.valueOf(faker.commerce().price(1, 100)))
                        .quantity(random.nextInt(100000) + 100)
                        .build())
                .process(exchange -> {
                    ProductSellRequest productSellRequest = exchange.getMessage().getBody(ProductSellRequest.class);
                    List<CompanyDetailsResponse> companyDetailsResponses = companyClient.findAll();
                    if (companyDetailsResponses.isEmpty()) {
                        exchange.setMessage(null);
                    }
                    int index = random.nextInt(companyDetailsResponses.size());
                    productSellRequest.setCompanyId(companyDetailsResponses.get(index).getId());
                })
                .choice()
                .when(body().isNull()).log(LoggingLevel.WARN, "No companies available for creating products")
                .otherwise()
                .process(exchange -> productClient.sell(exchange.getMessage().getBody(ProductSellRequest.class)))
                .endChoice()
                .end();

        from("timer://simpleTimer?period=5000&delay=1000")
                .routeId("cancel-product-route")
                .setBody(exchange -> productClient.countAllActive())
                .filter(body().isGreaterThan(ProductRoute.MINIMUM))
                .setBody(exchange -> productClient.findAllActive())
                .setBody(exchange -> {
                    List<ProductDetailsResponse> data = exchange.getMessage().getBody(List.class);
                    if (data.isEmpty()) {
                        return null;
                    }
                    int index = random.nextInt(data.size());
                    return data.get(index);
                })
                .choice()
                .when(body().isNull()).log(LoggingLevel.WARN, "No products available for cancelling")
                .otherwise()
                .process(exchange -> productClient.cancel(exchange.getMessage().getBody(ProductDetailsResponse.class).getId()))
                .endChoice()
                .end();

    }

}

