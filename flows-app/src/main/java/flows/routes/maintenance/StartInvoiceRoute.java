package flows.routes.maintenance;

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
                .setBody(exchange -> orderClient.findCompletedIds())
                .split(body())
                .multicast().parallelProcessing()
                .process(exchange -> {
                    Integer id = exchange.getIn().getBody(Integer.class);
                    log.info("start invoicing for {}", id);
                })
                .to("jms:queue:CompletedOrdersQueueName")
                .end();
    }

}

