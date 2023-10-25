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
                .to("rest:get:client/count")
                .unmarshal(new JacksonDataFormat(Long.class))
                .filter(body().isLessThan(platform.routes.clients.ClientRoute.MAXIMUM))
                .setBody(exchange -> Client.builder()
                        .name(faker.name().username())
                        .cardType(faker.business().creditCardType())
                        .country(faker.country().name())
                        .build())
                .to("rest:post:client")
                .unmarshal(new JacksonDataFormat(IdResponse.class))
                .log("Registered client with id : ${body.id}")
                .end();

        from("timer://simpleTimer?period=5000&delay=7500")
                .routeId("unregister-client-route")
                .to("rest:get:client")
                .unmarshal(new ListJacksonDataFormat(Client.class))
                .filter(body().method("size").isGreaterThan(platform.routes.clients.ClientRoute.MINIMUM))
                .setBody(exchange -> {
                    List<Client> data = exchange.getMessage().getBody(List.class);
                    int index = random.nextInt(data.size());
                    return data.get(index).getId();
                })
                .setHeader("id", body())
                .to("rest:delete:client/{id}")
                .log("Unregistered client with id : ${header.id}")
                .end();

    }


}

