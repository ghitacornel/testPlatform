package flows.routes;

import contracts.clients.ClientContract;
import contracts.clients.ClientDetailsResponse;
import contracts.companies.CompanyContract;
import contracts.companies.CompanyDetailsResponse;
import contracts.invoices.*;
import contracts.orders.OrderContract;
import contracts.orders.OrderDetailsResponse;
import contracts.products.ProductContract;
import contracts.products.ProductDetailsResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CompleteInvoiceRoute extends RouteBuilder {

    private final InvoiceContract invoiceContract;
    private final OrderContract orderContract;
    private final ClientContract clientContract;
    private final CompanyContract companyContract;
    private final ProductContract productContract;

    @Override
    public void configure() {

        from("jms:queue:CompletedOrdersQueueName")
                .routeId("create-invoice-route")
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
                    Integer productId = exchange.getMessage().getHeader("productId", Integer.class);
                    ProductDetailsResponse response;
                    try {
                        response = productContract.findById(productId);
                    } catch (FeignException e) {
                        log.error("no product found for id " + productId);
                        return;
                    }
                    invoiceContract.update(UpdateProductRequest.builder()
                            .id(orderId)
                            .productId(productId)
                            .productName(response.getName())
                            .productColor(response.getColor())
                            .productPrice(response.getPrice())
                            .companyId(response.getCompanyId())
                            .build());
                    exchange.getMessage().setHeader("companyId", response.getCompanyId());
                })
                .process(exchange -> {
                    Integer orderId = exchange.getMessage().getBody(Integer.class);
                    Integer clientId = exchange.getMessage().getHeader("clientId", Integer.class);
                    ClientDetailsResponse response;
                    try {
                        response = clientContract.findById(clientId);
                    } catch (FeignException e) {
                        log.error("no client found for id " + clientId);
                        return;
                    }
                    invoiceContract.update(UpdateClientRequest.builder()
                            .id(orderId)
                            .clientId(clientId)
                            .clientName(response.getName())
                            .clientCardType(response.getCardType())
                            .clientCountry(response.getCountry())
                            .build());
                })
                .process(exchange -> {
                    Integer orderId = exchange.getMessage().getBody(Integer.class);
                    Integer companyId = exchange.getMessage().getHeader("companyId", Integer.class);
                    CompanyDetailsResponse response;
                    try {
                        response = companyContract.findById(companyId);
                    } catch (FeignException e) {
                        log.error("no company found for id " + companyId);
                        return;
                    }
                    invoiceContract.update(UpdateCompanyRequest.builder()
                            .id(orderId)
                            .companyId(companyId)
                            .companyName(response.getName())
                            .companyUrl(response.getUrl())
                            .companyIndustry(response.getIndustry())
                            .companyCountry(response.getCountry())
                            .build());
                })
                .process(exchange -> {
                    Integer orderId = exchange.getMessage().getBody(Integer.class);
                    orderContract.complete(orderId);
                })
                .end();
    }

}

