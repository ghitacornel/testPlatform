package flows.routes;

import flows.clients.ClientClient;
import flows.clients.OrderClient;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteClientRoute extends RouteBuilder {

    private final ClientClient clientClient;
    private final OrderClient orderClient;

    @Override
    public void configure() {

        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest()
                .path("/clients/delete")
                .delete("/{id}")
                .to("direct:delete-client");

        from("direct:delete-client")
            .routeId("delete-client-route")
            .process(exchange -> {
                    Integer id = exchange.getIn().getHeader("id", Integer.class);
                    try {
                        clientClient.unregister(id);
                        orderClient.findAllNewForClientId(id)
                                .forEach(orderDetailsResponse -> orderClient.cancel(orderDetailsResponse.getId()));
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                })
            .log("End cancelling orders for client ${header.id}")
            .setBody().simple("${null}")
                .end();
    }

}

