package platform.simulators;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import platform.clients.CompanyClient;
import platform.clients.FlowsClient;
import platform.utils.GenerateUtils;

import java.util.List;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompanyRouteUnregister {

    public static final int MINIMUM = 50;

    private final Random random = new Random();

    private final CompanyClient companyClient;
    private final FlowsClient flowsClient;

    @Scheduled(fixedRate = 10000, initialDelay = 10000)
    void simulate() {
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

