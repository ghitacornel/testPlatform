package platform.routes;

import com.github.javafaker.Faker;
import contracts.clients.ClientContract;
import contracts.clients.ClientDetailsResponse;
import contracts.clients.ClientRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
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

    private final ClientContract clientContract;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=5000&delay=5000")
                .routeId("register-client-route")
                .setBody(exchange -> clientContract.count())
                .filter(body().isLessThan(ClientRoute.MAXIMUM))
                .setBody(exchange -> ClientRegisterRequest.builder()
                        .name(faker.name().username())
                        .cardType(faker.business().creditCardType())
                        .country(faker.country().name())
                        .build())
                .process(exchange -> clientContract.register(exchange.getMessage().getBody(ClientRegisterRequest.class)))
                .end();

        from("timer://simpleTimer?period=5000&delay=5000")
                .routeId("unregister-client-route")
                .setBody(exchange -> clientContract.findAll())
                .filter(body().method("size").isGreaterThan(ClientRoute.MINIMUM))
                .setBody(exchange -> {
                    List<ClientDetailsResponse> data = exchange.getMessage().getBody(List.class);
                    int index = random.nextInt(data.size());
                    return data.get(index).getId();
                })
                .process(exchange -> clientContract.unregister(exchange.getMessage().getBody(Integer.class)))
                .end();

    }

}

