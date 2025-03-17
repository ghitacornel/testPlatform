package platform.simulators;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ClientScheduler {

    private final ClientService service;

    @Scheduled(fixedRate = 5000, initialDelay = 5000)
    void register() {
        service.register();
    }

    @Scheduled(fixedRate = 5000, initialDelay = 5000)
    void unregister() {
        service.unregister();
    }

}

