package flows.routes;

import contracts.clients.ClientContract;
import contracts.clients.ClientDetailsResponse;
import contracts.companies.CompanyContract;
import contracts.invoices.InvoiceContract;
import contracts.invoices.InvoiceCreateRequest;
import contracts.invoices.UpdateClientRequest;
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
    private final ClientContract clientContract;
    private final CompanyContract companyContract;
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
                    OrderDetailsResponse response = orderContract.findById(orderId);
                    invoiceContract.update(UpdateOrderRequest.builder()
                            .id(orderId)
                            .clientId(response.getClientId())
                            .productId(response.getProductId())
                            .orderQuantity(response.getQuantity())
                            .build());
                    exchange.getMessage().setHeader("clientId", response.getClientId());
                    exchange.getMessage().setHeader("productId", response.getProductId());
                })
                .process(exchange -> {
                    Integer orderId = exchange.getMessage().getBody(Integer.class);
                    Integer clientId = exchange.getMessage().getHeader("clientId", Integer.class);
                    ClientDetailsResponse response = clientContract.findById(clientId);
                    invoiceContract.update(UpdateClientRequest.builder()
                            .id(orderId)
                            .clientId(response.getId())
                            .clientName(response.getName())
                            .clientCardType(response.getCardType())
                            .clientCountry(response.getCountry())
                            .build());
                })
                .end();
    }

}

