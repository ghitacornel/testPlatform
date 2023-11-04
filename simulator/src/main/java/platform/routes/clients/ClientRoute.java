package platform.routes.clients;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.routes.clients.feign.ClientContract;
import platform.routes.clients.feign.ClientDetailsResponse;
import platform.routes.clients.feign.ClientRegisterRequest;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class ClientRoute extends RouteBuilder {

    private static final int MINIMUM = 100;
    private static final int MAXIMUM = 200;

    private final Random random = new Random();
    private final Faker faker = Faker.instance();

    private final ClientContract clientContract;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=500&delay=500")
                .routeId("register-client-route")
                .setBody(exchange -> clientContract.count())
                .filter(body().isLessThan(platform.routes.clients.ClientRoute.MAXIMUM))
                .setBody(exchange -> ClientRegisterRequest.builder()
                        .name(faker.name().username())
                        .cardType(faker.business().creditCardType())
                        .country(faker.country().name())
                        .build())
                .setBody(exchange -> clientContract.register(exchange.getMessage().getBody(ClientRegisterRequest.class)))
                .log("Registered client with id : ${body.id}")
                .end();

        from("timer://simpleTimer?period=500&delay=750")
                .routeId("unregister-client-route")
                .setBody(exchange -> clientContract.findAll())
                .filter(body().method("size").isGreaterThan(platform.routes.clients.ClientRoute.MINIMUM))
                .setBody(exchange -> {
                    List<ClientDetailsResponse> data = exchange.getMessage().getBody(List.class);
                    int index = random.nextInt(data.size());
                    return data.get(index).getId();
                })
                .process(exchange -> clientContract.unregister(exchange.getMessage().getBody(Integer.class)))
                .log("Unregistered client with id : ${body}")
                .end();

    }

}

