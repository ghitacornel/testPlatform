package flows.routes;

import flows.feign.order.OrderContract;
import flows.feign.order.OrderDetailsResponse;
import flows.feign.product.ProductContract;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompleteInvoiceRoute extends RouteBuilder {


    private final OrderContract orderContract;
    private final ProductContract productContract;

    @Override
    public void configure() {

        from("jms:queue:CompletedOrdersQueueName")
                .process(exchange -> {
                    Integer orderId = exchange.getMessage().getBody(Integer.class);
                    OrderDetailsResponse orderDetailsResponse = orderContract.findById(orderId);
                })
                .end();
    }

}

