package platform.simulators;

import contracts.companies.CompanyRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import platform.clients.CompanyClient;
import platform.clients.FlowsClient;
import platform.fakers.CompanyRegisterRequestFaker;
import platform.utils.GenerateUtils;

import java.util.List;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompanyService {

    public static final int MINIMUM = 50;
    private static final int MAXIMUM = 100;

    private final Random random = new Random();

    private final CompanyClient companyClient;
    private final FlowsClient flowsClient;

    @Async
    void register() {
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

    @Async
    void unregister() {
        long count = companyClient.count();
        if (count < MINIMUM) {
            return;
        }

        List<Integer> activeIds = companyClient.findActiveIds();
        Integer id = GenerateUtils.random(activeIds, random);
        if (id == null) {
            log.warn("No active companies available for unregistering");
            return;
        }

        try {
            flowsClient.deleteCompany(id);
            log.info("unregister company {}", id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}

