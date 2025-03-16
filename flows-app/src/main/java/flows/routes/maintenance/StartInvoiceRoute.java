package flows.routes.maintenance;

import commons.exceptions.BusinessException;
import commons.exceptions.RestTechnicalException;
import feign.FeignException;
import flows.clients.OrderClient;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartInvoiceRoute extends RouteBuilder {

    private final OrderClient orderClient;

    @Override
    public void configure() {
        from("timer://simpleTimer?period=10000&delay=1000")
                .routeId("start-invoice-route")
                .setBody(exchange -> {
                    try {
                        return orderClient.findCompletedIds();
                    } catch (BusinessException | RestTechnicalException | FeignException e) {
                        log.error(e.getMessage());
                        return null;
                    }
                })
                .split(body())
                .multicast().parallelProcessing()
                .process(exchange -> {
                    Integer id = exchange.getIn().getBody(Integer.class);
                    orderClient.markAsSentToInvoice(id);
                    log.info("start invoicing for {}", id);
                })
                .to("jms:queue:CompletedOrdersQueueName")
                .end();
    }

}

