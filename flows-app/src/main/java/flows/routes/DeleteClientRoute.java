package flows.routes;

import flows.clients.ClientClient;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteClientRoute extends RouteBuilder {

    private final ClientClient clientClient;

    @Override
    public void configure() {

        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest()
                .path("/client/delete")
                .delete("/{id}")
                .to("direct:delete-client");

        from("direct:delete-client")
                .routeId("delete-client-route")
                .log("Unregister client ${header.id}")
                .process(exchange -> {
                    Integer id = exchange.getIn().getHeader("id", Integer.class);
                    clientClient.unregister(id);
                })
                .log("Unregistered client ${header.id}")
                .log("Delete client ${header.id}")
                .process(exchange -> {
                    Integer id = exchange.getIn().getHeader("id", Integer.class);
                    clientClient.delete(id);
                })
                .log("Deleted client ${header.id}")
                .setBody().simple("${null}")
                .end();
    }

}

