package flows.routes;

import contracts.invoices.InvoiceContract;
import contracts.invoices.InvoiceCreateRequest;
import contracts.invoices.UpdateOrderRequest;
import contracts.orders.OrderContract;
import contracts.orders.OrderDetailsResponse;
import contracts.products.ProductContract;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompleteInvoiceRoute extends RouteBuilder {

    private final InvoiceContract invoiceContract;
    private final OrderContract orderContract;
    private final ProductContract productContract;

    @Override
    public void configure() {

        from("jms:queue:CompletedOrdersQueueName")
                .process(exchange -> {
                    Integer orderId = exchange.getMessage().getBody(Integer.class);
                    invoiceContract.create(InvoiceCreateRequest.builder()
                            .orderId(orderId)
                            .build());
                })
                .process(exchange -> {
                    Integer orderId = exchange.getMessage().getBody(Integer.class);
                    OrderDetailsResponse orderDetailsResponse = orderContract.findById(orderId);
                    invoiceContract.update(UpdateOrderRequest.builder()
                            .id(orderId)
                            .orderQuantity(orderDetailsResponse.getQuantity())
                            .build());
                })
                .end();
    }

}

