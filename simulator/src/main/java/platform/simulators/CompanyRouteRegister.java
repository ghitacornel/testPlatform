package platform.simulators;

import contracts.companies.CompanyRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import platform.clients.CompanyClient;
import platform.fakers.CompanyRegisterRequestFaker;

@Slf4j
@Component
@RequiredArgsConstructor
class CompanyRouteRegister {

    private static final int MAXIMUM = 100;

    private final CompanyClient companyClient;

    @Scheduled(fixedRate = 10000, initialDelay = 10000)
    void simulate() {
        long count = companyClient.count();
        if (count > MAXIMUM) {
            return;
        }

        CompanyRegisterRequest fake = CompanyRegisterRequestFaker.fake();

        try {
            companyClient.register(fake);
            log.info("register company {}", fake);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
}

