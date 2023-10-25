package platform.routes.clients;

import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
public class ClientRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("timer://simpleTimer?period=5000")
                .routeId("register-client-route")
                .log("register new client ${body}")
                .end();
    }
}

