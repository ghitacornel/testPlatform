package platform.routes.companies;

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
public class CompanyRoute extends RouteBuilder {

    private static final int MINIMUM = 50;
    private static final int MAXIMUM = 100;
    private final Random random = new Random();
    private final Faker faker = Faker.instance();

    @Override
    public void configure() {

        restConfiguration().host("localhost").port(8091).contextPath("company").component("servlet").bindingMode(RestBindingMode.json);

        from("timer://simpleTimer?period=500&delay=500")
                .routeId("register-company-route")
                .to("rest:get:company/count")
                .unmarshal(new JacksonDataFormat(Long.class))
                .filter(body().isLessThan(CompanyRoute.MAXIMUM))
                .setBody(exchange -> Company.builder()
                        .name(faker.company().name())
                        .industry(faker.company().industry())
                        .url(faker.company().url())
                        .country(faker.country().name())
                        .build())
                .to("rest:post:company")
                .unmarshal(new JacksonDataFormat(IdResponse.class))
                .log("Registered company with id : ${body.id}")
                .end();

        from("timer://simpleTimer?period=500&delay=750")
                .routeId("unregister-company-route")
                .to("rest:get:company")
                .unmarshal(new ListJacksonDataFormat(Company.class))
                .filter(body().method("size").isGreaterThan(CompanyRoute.MINIMUM))
                .setBody(exchange -> {
                    List<Company> data = exchange.getMessage().getBody(List.class);
                    int index = random.nextInt(data.size());
                    return data.get(index).getId();
                })
                .setHeader("id", body())
                .to("rest:delete:company/{id}")
                .log("Unregistered company with id : ${header.id}")
                .end();

    }

}

