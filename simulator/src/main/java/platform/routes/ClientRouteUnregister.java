package platform.routes;

import lombok.RequiredArgsConstructor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.clients.ClientClient;
import platform.clients.FlowsClient;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class ClientRouteUnregister extends RouteBuilder {

    public static final int MINIMUM = 100;

    private final Random random = new Random();

    private final ClientClient clientClient;
    private final FlowsClient flowsClient;

    @Override
    public void configure() {
        from("timer://simpleTimer?period=5000&delay=5000")
                .routeId("unregister-client-route")
                .setBody(exchange -> clientClient.count())
                .filter(body().isGreaterThan(ClientRouteUnregister.MINIMUM))
                .setBody(exchange -> clientClient.findActiveIds())
                .filter(body().method("size").isGreaterThan(ClientRouteUnregister.MINIMUM))
                .setBody(exchange -> {
                    List<?> data = exchange.getMessage().getBody(List.class);
                    if (data.isEmpty()) {
                        return null;
                    }
                    int index = random.nextInt(data.size());
                    return data.get(index);
                })
                .choice()
                .when(body().isNull()).log(LoggingLevel.WARN, "No clients available for unregistering")
                .otherwise()
                .process(exchange -> flowsClient.deleteClient(exchange.getMessage().getBody(Integer.class)))
                .endChoice()
                .end();
    }

}

