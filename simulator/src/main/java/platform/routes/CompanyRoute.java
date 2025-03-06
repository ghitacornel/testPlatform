package platform.routes;

import com.github.javafaker.Faker;
import contracts.companies.CompanyDetailsResponse;
import contracts.companies.CompanyRegisterRequest;
import platform.clients.FlowsClient;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.clients.CompanyClient;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class CompanyRoute extends RouteBuilder {

    private static final int MINIMUM = 50;
    private static final int MAXIMUM = 100;

    private final Random random = new Random();
    private final Faker faker = Faker.instance();

    private final CompanyClient companyClient;
    private final FlowsClient flowsClient;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=10000&delay=10000")
                .routeId("register-company-route")
                .setBody(exchange -> companyClient.count())
                .filter(body().isLessThan(CompanyRoute.MAXIMUM))
                .setBody(exchange -> CompanyRegisterRequest.builder()
                        .name(faker.company().name())
                        .industry(faker.company().industry())
                        .url(faker.company().url())
                        .country(faker.country().name())
                        .build())
                .process(exchange -> companyClient.create(exchange.getMessage().getBody(CompanyRegisterRequest.class)))
                .end();

        from("timer://simpleTimer?period=10000&delay=10000")
                .routeId("unregister-company-route")
                .setBody(exchange -> companyClient.count())
                .filter(body().isGreaterThan(CompanyRoute.MINIMUM))
                .setBody(exchange -> companyClient.findAll())
                .filter(body().method("size").isGreaterThan(CompanyRoute.MINIMUM))
                .setBody(exchange -> {
                    List<CompanyDetailsResponse> data = exchange.getMessage().getBody(List.class);
                    if (data.isEmpty()) {
                        return null;
                    }
                    int index = random.nextInt(data.size());
                    return data.get(index).getId();
                })
                .choice()
                .when(body().isNull()).log("No companies available for unregistering")
                .otherwise()
                .process(exchange -> flowsClient.deleteCompany(exchange.getMessage().getBody(Integer.class)))
                .endChoice()
                .end();

    }

}

