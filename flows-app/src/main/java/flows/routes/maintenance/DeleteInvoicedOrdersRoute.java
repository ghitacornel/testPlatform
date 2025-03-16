package flows.routes.maintenance;

import commons.exceptions.RestTechnicalException;
import feign.FeignException;
import flows.clients.InvoiceClient;
import flows.clients.OrderClient;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteInvoicedOrdersRoute extends RouteBuilder {

    private final OrderClient orderClient;
    private final InvoiceClient invoiceClient;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=10000&delay=1000")
                .routeId("delete-invoiced-order-route")
                .setBody(exchange -> orderClient.findInvoicedIds())
                .split(body())
                .parallelProcessing()
                .process(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    try {
                        if (invoiceClient.existsByOrderId(id)) {
                            return;
                        }
                        orderClient.delete(id);
                        log.info("Invoiced order deleted {}", id);
                    } catch (RestTechnicalException | FeignException e) {
                        log.error(e.getMessage());
                    }
                })
                .end();
    }

}

