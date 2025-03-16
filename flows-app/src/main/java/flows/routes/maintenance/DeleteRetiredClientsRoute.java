package flows.routes.maintenance;

import commons.exceptions.RestTechnicalException;
import feign.FeignException;
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
                .process(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    try {
                        if (orderClient.existsByClientId(id)) {
                            return;
                        }
                        if (invoiceClient.existsByClientId(id)) {
                            return;
                        }
                        clientClient.delete(id);
                        log.info("Retired client deleted {}", id);
                    } catch (RestTechnicalException | FeignException e) {
                        log.error(e.getMessage());
                    }
                })
                .end();
    }

}

