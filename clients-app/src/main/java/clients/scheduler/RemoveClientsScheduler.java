package clients.scheduler;

import clients.service.RemoveClientsSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class RemoveClientsScheduler {

    private final RemoveClientsSchedulerService service;

    @Scheduled(fixedRate = 10000)
    private void removeRetiredClients() {
        service.removeRetiredClients();
    }

}
