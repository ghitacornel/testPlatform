package platform.routes;

import commons.exceptions.RestTechnicalException;
import contracts.companies.CompanyRegisterRequest;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.clients.CompanyClient;
import platform.fakers.CompanyRegisterRequestFaker;

@Component
@RequiredArgsConstructor
public class CompanyRouteRegister extends RouteBuilder {

    private static final int MAXIMUM = 100;


    private final CompanyClient companyClient;

    @Override
    public void configure() {
        from("timer://simpleTimer?period=10000&delay=10000")
                .routeId("register-company-route")
                .setBody(exchange -> companyClient.count())
                .filter(body().isLessThan(CompanyRouteRegister.MAXIMUM))
                .setBody(exchange -> CompanyRegisterRequestFaker.fake())
                .process(exchange -> {
                    try {
                        companyClient.register(exchange.getMessage().getBody(CompanyRegisterRequest.class));
                    } catch (RestTechnicalException | FeignException e) {
                        log.error(e.getMessage());
                    }
                })
                .end();
    }

}