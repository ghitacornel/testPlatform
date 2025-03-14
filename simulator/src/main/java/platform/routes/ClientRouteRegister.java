package platform.routes;

import contracts.clients.ClientRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.clients.ClientClient;
import platform.fakers.ClientRegisterRequestFaker;

@Component
@RequiredArgsConstructor
public class ClientRouteRegister extends RouteBuilder {

    private static final int MAXIMUM = 200;

    private final ClientClient clientClient;

    @Override
    public void configure() {
        from("timer://simpleTimer?period=5000&delay=5000")
                .routeId("register-client-route")
                .setBody(exchange -> clientClient.count())
                .filter(body().isLessThan(ClientRouteRegister.MAXIMUM))
                .setBody(exchange -> ClientRegisterRequestFaker.fake())
                .process(exchange -> clientClient.register(exchange.getMessage().getBody(ClientRegisterRequest.class)))
                .end();
    }

}

