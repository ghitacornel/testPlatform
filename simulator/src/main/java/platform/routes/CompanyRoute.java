package platform.routes;

import com.github.javafaker.Faker;
import contracts.companies.CompanyDetailsResponse;
import contracts.companies.CompanyRegisterRequest;
import platform.feign.FlowsContract;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.feign.CompanyContract;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class CompanyRoute extends RouteBuilder {

    private static final int MINIMUM = 50;
    private static final int MAXIMUM = 100;

    private final Random random = new Random();
    private final Faker faker = Faker.instance();

    private final CompanyContract companyContract;
    private final FlowsContract flowsContract;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=10000&delay=10000")
                .routeId("register-company-route")
                .setBody(exchange -> companyContract.count())
                .filter(body().isLessThan(CompanyRoute.MAXIMUM))
                .setBody(exchange -> CompanyRegisterRequest.builder()
                        .name(faker.company().name())
                        .industry(faker.company().industry())
                        .url(faker.company().url())
                        .country(faker.country().name())
                        .build())
                .process(exchange -> companyContract.create(exchange.getMessage().getBody(CompanyRegisterRequest.class)))
                .end();

        from("timer://simpleTimer?period=10000&delay=10000")
                .routeId("unregister-company-route")
                .setBody(exchange -> companyContract.findAll())
                .filter(body().method("size").isGreaterThan(CompanyRoute.MINIMUM))
                .setBody(exchange -> {
                    List<CompanyDetailsResponse> data = exchange.getMessage().getBody(List.class);
                    int index = random.nextInt(data.size());
                    return data.get(index).getId();
                })
                .process(exchange -> flowsContract.deleteCompany(exchange.getMessage().getBody(Integer.class)))
                .end();

    }

}

