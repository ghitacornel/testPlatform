package flows.routes.maintenance;

import flows.clients.ClientClient;
import flows.clients.InvoiceClient;
import flows.clients.OrderClient;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteRetiredClientsRoute extends RouteBuilder {

    private final ClientClient clientClient;
    private final OrderClient orderClient;
    private final InvoiceClient invoiceClient;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=10000&delay=1000")
                .routeId("delete-retired-clients-route")
                .setBody(exchange -> clientClient.findRetiredIds())
                .split(body())
                .parallelProcessing()
                .setBody(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    return orderClient.existsByClientId(id) ? null : id;
                })
                .filter(body().isNotNull())
                .setBody(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    return invoiceClient.existsByClientId(id) ? null : id;
                })
                .filter(body().isNotNull())
                .process(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    clientClient.delete(id);
                })
                .log("Retired client deleted ${body}")
                .end();
    }

}

