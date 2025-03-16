package platform.routes;

import commons.exceptions.RestTechnicalException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.clients.CompanyClient;
import platform.clients.FlowsClient;
import platform.utils.GenerateUtils;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class CompanyRouteUnregister extends RouteBuilder {

    public static final int MINIMUM = 50;

    private final Random random = new Random();

    private final CompanyClient companyClient;
    private final FlowsClient flowsClient;

    @Override
    public void configure() {
        from("timer://simpleTimer?period=10000&delay=10000")
                .routeId("unregister-company-route")
                .setBody(exchange -> companyClient.count())
                .filter(body().isGreaterThan(CompanyRouteUnregister.MINIMUM))
                .setBody(exchange -> companyClient.findActiveIds())
                .filter(body().method("size").isGreaterThan(CompanyRouteUnregister.MINIMUM))
                .setBody(exchange -> GenerateUtils.random(exchange.getMessage().getBody(List.class), random))
                .choice()
                .when(body().isNull()).log(LoggingLevel.WARN, "No companies available for unregistering")
                .otherwise()
                .process(exchange -> {
                    try {
                        flowsClient.deleteCompany(exchange.getMessage().getBody(Integer.class));
                    } catch (RestTechnicalException | FeignException e) {
                        log.error(e.getMessage());
                    }
                })
                .endChoice()
                .end();
    }

}

