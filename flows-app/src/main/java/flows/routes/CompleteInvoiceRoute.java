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
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
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
                    OrderDetailsResponse response;
                    try {
                        response = orderClient.findById(id);
                    } catch (FeignException e) {
                        log.error(e.getMessage());
                        invoiceClient.error(id);
                        return;
                    }
                    invoiceClient.update(UpdateOrderRequest.builder()
                            .id(id)
                            .clientId(response.getClientId())
                            .productId(response.getProductId())
                            .orderQuantity(response.getQuantity())
                            .build());
                    exchange.getMessage().setHeader("clientId", response.getClientId());
                    exchange.getMessage().setHeader("productId", response.getProductId());
                })
                .process(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    Integer productId = exchange.getMessage().getHeader("productId", Integer.class);
                    ProductDetailsResponse response;
                    try {
                        response = productClient.findById(productId);
                    } catch (FeignException e) {
                        log.error(e.getMessage());
                        invoiceClient.error(id);
                        return;
                    }
                    invoiceClient.update(UpdateProductRequest.builder()
                            .id(id)
                            .productId(productId)
                            .productName(response.getName())
                            .productColor(response.getColor())
                            .productPrice(response.getPrice())
                            .companyId(response.getCompanyId())
                            .build());
                    exchange.getMessage().setHeader("companyId", response.getCompanyId());
                })
                .process(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    Integer clientId = exchange.getMessage().getHeader("clientId", Integer.class);
                    ClientDetailsResponse response;
                    try {
                        response = clientClient.findById(clientId);
                    } catch (FeignException e) {
                        log.error(e.getMessage());
                        invoiceClient.error(id);
                        return;
                    }
                    invoiceClient.update(UpdateClientRequest.builder()
                            .id(id)
                            .clientId(clientId)
                            .clientName(response.getName())
                            .clientCardType(response.getCardType())
                            .clientCountry(response.getCountry())
                            .build());
                })
                .process(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    Integer companyId = exchange.getMessage().getHeader("companyId", Integer.class);
                    CompanyDetailsResponse response;
                    try {
                        response = companyClient.findById(companyId);
                    } catch (FeignException e) {
                        log.error(e.getMessage());
                        invoiceClient.error(id);
                        return;
                    }
                    invoiceClient.update(UpdateCompanyRequest.builder()
                            .id(id)
                            .companyId(companyId)
                            .companyName(response.getName())
                            .companyUrl(response.getUrl())
                            .companyIndustry(response.getIndustry())
                            .companyCountry(response.getCountry())
                            .build());
                })
                .process(exchange -> invoiceClient.complete(exchange.getMessage().getBody(Integer.class)))
                .end();
    }

}

