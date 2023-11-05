package flows.routes;

import commons.model.IdResponse;
import flows.feign.order.CreateOrderRequest;
import flows.feign.order.OrderContract;
import flows.feign.product.ProductBuyRequest;
import flows.feign.product.ProductContract;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class CompleteInvoiceRoute extends RouteBuilder {


    private final OrderContract orderContract;
    private final ProductContract productContract;

    @Override
    public void configure() {

        from("jms:queue:CompletedOrdersQueueName")
                .log("got completed from jms ${body}")
                .end();
    }

}

