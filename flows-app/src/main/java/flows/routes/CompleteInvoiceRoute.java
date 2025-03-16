package flows.routes;

import commons.exceptions.BusinessException;
import commons.exceptions.RestTechnicalException;
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
                        return orderClient.findById(id);
                    } catch (FeignException e) {
                        log.warn("No order found with id {}", id);
                        return null;
                    }
                })
                .filter(body().isNotNull())
                .setBody(exchange -> {
                    OrderDetailsResponse orderDetails = exchange.getMessage().getBody(OrderDetailsResponse.class);
                    try {
                        invoiceClient.create(InvoiceCreateRequest.builder()
                                .orderId(orderDetails.getId())
                                .build());
                        log.info("Invoice created {}", orderDetails.getId());
                        return orderDetails;
                    } catch (FeignException e) {
                        log.error(e.getMessage());
                        return null;
                    }
                })
                .filter(body().isNotNull())
                .process(exchange -> {

                    OrderDetailsResponse orderDetails = exchange.getMessage().getBody(OrderDetailsResponse.class);
                    Integer id = orderDetails.getId();

                    try {

                        invoiceClient.update(UpdateOrderRequest.builder()
                                .id(id)
                                .clientId(orderDetails.getClientId())
                                .productId(orderDetails.getProductId())
                                .orderQuantity(orderDetails.getQuantity())
                                .build());

                        ProductDetailsResponse productDetails = productClient.findById(orderDetails.getProductId());
                        invoiceClient.update(UpdateProductRequest.builder()
                                .id(id)
                                .productId(productDetails.getId())
                                .productName(productDetails.getName())
                                .productColor(productDetails.getColor())
                                .productPrice(productDetails.getPrice())
                                .companyId(productDetails.getCompanyId())
                                .build());

                        ClientDetailsResponse clientDetails = clientClient.findById(orderDetails.getClientId());
                        invoiceClient.update(UpdateClientRequest.builder()
                                .id(id)
                                .clientId(clientDetails.getId())
                                .clientName(clientDetails.getName())
                                .clientCardType(clientDetails.getCardType())
                                .clientCountry(clientDetails.getCountry())
                                .build());

                        CompanyDetailsResponse companyDetails = companyClient.findById(productDetails.getCompanyId());
                        invoiceClient.update(UpdateCompanyRequest.builder()
                                .id(id)
                                .companyId(companyDetails.getId())
                                .companyName(companyDetails.getName())
                                .companyUrl(companyDetails.getUrl())
                                .companyIndustry(companyDetails.getIndustry())
                                .companyCountry(companyDetails.getCountry())
                                .build());

                        invoiceClient.complete(id);

                    } catch (BusinessException | RestTechnicalException | FeignException e) {
                        log.error(e.getMessage());
                        invoiceClient.error(id);
                    }

                })
                .end();
    }

}

