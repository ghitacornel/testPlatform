package platform.routes.clients;

import com.github.javafaker.Faker;
import commons.model.IdResponse;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class ClientRoute extends RouteBuilder {

    private static final int MINIMUM = 100;
    private static final int MAXIMUM = 200;
    private final Random random = new Random();
    private final Faker faker = Faker.instance();

    @Override
    public void configure() {

        restConfiguration().host("localhost:8090");
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        from("timer://simpleTimer?period=5000&delay=5000")
                .routeId("register-client-route")
                .to("rest:get:client")
                .unmarshal(new ListJacksonDataFormat(Client.class))
                .process(exchange -> {
                    List<?> data = exchange.getMessage().getBody(List.class);
                    if (data.size() > MAXIMUM) {
                        exchange.setRouteStop(true);
                        return;
                    }
                    exchange.getMessage().setBody(Client.builder()
                            .name(faker.name().username())
                            .cardType(faker.business().creditCardType())
                            .country(faker.country().name())
                            .build());
                })
                .to("rest:post:client")
                .unmarshal(new JacksonDataFormat(IdResponse.class))
                .log("Registered client with id : ${body.id}")
                .end();

        from("timer://simpleTimer?period=5000&delay=7500")
                .routeId("unregister-client-route")
                .to("rest:get:client")
                .unmarshal(new ListJacksonDataFormat(Client.class))
                .process(exchange -> {
                    List<Client> data = exchange.getMessage().getBody(List.class);
                    if (data.size() < MINIMUM) {
                        exchange.setRouteStop(true);
                        return;
                    }
                    int index = random.nextInt(data.size());
                    Client client = data.get(index);
                    exchange.getMessage().setBody(client.getId());
                })
                .setHeader("id", body())
                .to("rest:delete:client/{id}")
                .log("Unregistered client with id : ${header.id}")
                .end();

    }


}

