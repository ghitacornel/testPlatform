package platform.routes;

import contracts.clients.ClientDetailsResponse;
import contracts.clients.ClientRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.clients.ClientClient;
import platform.clients.FlowsClient;
import platform.fakers.ClientRegisterRequestFaker;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class ClientRoute extends RouteBuilder {

    public static final int MINIMUM = 100;
    private static final int MAXIMUM = 200;

    private final Random random = new Random();

    private final ClientClient clientClient;
    private final FlowsClient flowsClient;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=5000&delay=5000")
                .routeId("register-client-route")
                .setBody(exchange -> clientClient.count())
                .filter(body().isLessThan(ClientRoute.MAXIMUM))
                .setBody(exchange -> ClientRegisterRequestFaker.fake())
                .process(exchange -> clientClient.register(exchange.getMessage().getBody(ClientRegisterRequest.class)))
                .end();

        from("timer://simpleTimer?period=5000&delay=5000")
                .routeId("unregister-client-route")
                .setBody(exchange -> clientClient.count())
                .filter(body().isGreaterThan(ClientRoute.MINIMUM))
                .setBody(exchange -> clientClient.findAll())
                .filter(body().method("size").isGreaterThan(ClientRoute.MINIMUM))
                .setBody(exchange -> {
                    List<ClientDetailsResponse> data = exchange.getMessage().getBody(List.class);
                    if (data.isEmpty()) {
                        return null;
                    }
                    int index = random.nextInt(data.size());
                    return data.get(index).getId();
                })
                .choice()
                .when(body().isNull()).log(LoggingLevel.WARN, "No clients available for unregistering")
                .otherwise()
                .process(exchange -> flowsClient.deleteClient(exchange.getMessage().getBody(Integer.class)))
                .endChoice()
                .end();

    }

}

