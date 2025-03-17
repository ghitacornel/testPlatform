package platform.simulators;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CompanyScheduler {

    private final CompanyService service;

    @Scheduled(fixedRate = 10000, initialDelay = 10000)
    void register() {
        service.register();
    }

    @Scheduled(fixedRate = 10000, initialDelay = 10000)
    void unregister() {
        service.unregister();
    }

}

