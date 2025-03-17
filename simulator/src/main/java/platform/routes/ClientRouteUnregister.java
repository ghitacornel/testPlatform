package platform.routes;

import lombok.RequiredArgsConstructor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.clients.ClientClient;
import platform.clients.FlowsClient;
import platform.utils.GenerateUtils;

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
            .filter(body()
            .method("size")
            .isGreaterThan(ClientRouteUnregister.MINIMUM)).setBody(exchange -> GenerateUtils.random(exchange.getMessage().getBody(List.class), random)).choice()
            .when(body().isNull())
                .log(LoggingLevel.WARN, "No clients available for unregistering")
            .otherwise()
                .process(exchange -> {
                    try {
                        flowsClient.deleteClient(exchange.getMessage().getBody(Integer.class));
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                })
            .endChoice().end();
    }

}

