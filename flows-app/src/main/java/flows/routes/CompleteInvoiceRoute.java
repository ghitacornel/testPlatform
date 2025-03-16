package flows.routes;

import contracts.clients.ClientDetailsResponse;
import contracts.companies.CompanyDetailsResponse;
import contracts.invoices.*;
import contracts.orders.OrderDetailsResponse;
import contracts.products.ProductDetailsResponse;
import feign.FeignException;
import flows.clients.*;
import flows.clients.InvoiceClient;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompleteInvoiceRoute extends RouteBuilder {

    private final InvoiceClient invoiceClient;
    private final OrderClient orderClient;
    private final ClientClient clientClient;
    private final CompanyClient companyClient;
    private final ProductClient productClient;

    @Override
    public void configure() {

        from("jms:queue:CompletedOrdersQueueName")
                .routeId("create-invoice-route")
                .setBody(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    try {
                        invoiceClient.create(InvoiceCreateRequest.builder()
                                .orderId(id)
                                .build());
                        log.info("Invoice created {}", id);
                        return id;
                    } catch (FeignException e) {
                        log.error(e.getMessage());
                        return null;
                    }
                })
                .filter(body().isNotNull())
                .process(exchange -> {

                    Integer id = exchange.getMessage().getBody(Integer.class);

                    try {


                        OrderDetailsResponse orderDetailsResponse = orderClient.findById(id);
                        invoiceClient.update(UpdateOrderRequest.builder()
                                .id(id)
                                .clientId(orderDetailsResponse.getClientId())
                                .productId(orderDetailsResponse.getProductId())
                                .orderQuantity(orderDetailsResponse.getQuantity())
                                .build());


                        ProductDetailsResponse productDetailsResponse = productClient.findById(orderDetailsResponse.getProductId());
                        invoiceClient.update(UpdateProductRequest.builder()
                                .id(id)
                                .productId(productDetailsResponse.getId())
                                .productName(productDetailsResponse.getName())
                                .productColor(productDetailsResponse.getColor())
                                .productPrice(productDetailsResponse.getPrice())
                                .companyId(productDetailsResponse.getCompanyId())
                                .build());

                        ClientDetailsResponse clientDetailsResponse = clientClient.findById(orderDetailsResponse.getClientId());
                        invoiceClient.update(UpdateClientRequest.builder()
                                .id(id)
                                .clientId(clientDetailsResponse.getId())
                                .clientName(clientDetailsResponse.getName())
                                .clientCardType(clientDetailsResponse.getCardType())
                                .clientCountry(clientDetailsResponse.getCountry())
                                .build());

                        CompanyDetailsResponse companyDetailsResponse = companyClient.findById(productDetailsResponse.getCompanyId());
                        invoiceClient.update(UpdateCompanyRequest.builder()
                                .id(id)
                                .companyId(companyDetailsResponse.getId())
                                .companyName(companyDetailsResponse.getName())
                                .companyUrl(companyDetailsResponse.getUrl())
                                .companyIndustry(companyDetailsResponse.getIndustry())
                                .companyCountry(companyDetailsResponse.getCountry())
                                .build());

                        invoiceClient.complete(exchange.getMessage().getBody(Integer.class));

                    } catch (FeignException e) {
                        log.error(e.getMessage());
                    }

                })
                .end();
    }

}

