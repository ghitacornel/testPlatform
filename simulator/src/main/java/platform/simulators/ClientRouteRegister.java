package platform.simulators;

import contracts.clients.ClientRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import platform.clients.ClientClient;
import platform.fakers.ClientRegisterRequestFaker;

@Slf4j
@Component
@RequiredArgsConstructor
class ClientRouteRegister {

    private static final int MAXIMUM = 200;

    private final ClientClient clientClient;

    @Scheduled(fixedRate = 5000, initialDelay = 5000)
    void simulate() {

        long count = clientClient.count();
        if (count > MAXIMUM) {
            return;
        }
        ClientRegisterRequest fake = ClientRegisterRequestFaker.fake();

        try {
            clientClient.register(fake);
            log.info("register client {}", fake);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}

