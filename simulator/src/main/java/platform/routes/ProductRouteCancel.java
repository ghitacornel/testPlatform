package platform.routes;

import lombok.RequiredArgsConstructor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.clients.ProductClient;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class ProductRouteCancel extends RouteBuilder {

    public static final int MINIMUM = 50;

    private final Random random = new Random();

    private final ProductClient productClient;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=5000&delay=1000")
                .routeId("cancel-product-route-timer")
                .multicast()
                .parallelProcessing()
                .to("direct:cancel-product-route")
                .to("direct:cancel-product-route")
                .to("direct:cancel-product-route")
                .to("direct:cancel-product-route")
                .to("direct:cancel-product-route")
        ;

        from("direct:cancel-product-route")
                .routeId("cancel-product-route")
                .setBody(exchange -> productClient.countAllActive())
                .filter(body().isGreaterThan(ProductRouteCancel.MINIMUM))
                .setBody(exchange -> productClient.findActiveIds())
                .setBody(exchange -> {
                    List<?> data = exchange.getMessage().getBody(List.class);
                    if (data.isEmpty()) {
                        return null;
                    }
                    int index = random.nextInt(data.size());
                    return data.get(index);
                })
                .choice()
                .when(body().isNull()).log(LoggingLevel.WARN, "No products available for cancelling")
                .otherwise()
                .process(exchange -> productClient.cancel(exchange.getMessage().getBody(Integer.class)))
                .log("Cancel product " + body())
                .endChoice()
                .end();

    }

}

