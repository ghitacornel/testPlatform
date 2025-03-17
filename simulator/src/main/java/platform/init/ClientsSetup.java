package platform.init;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import platform.clients.ClientClient;
import platform.fakers.ClientRegisterRequestFaker;

import static platform.simulators.ClientRouteUnregister.MINIMUM;

@Component
@RequiredArgsConstructor
class ClientsSetup {

    private final ClientClient client;

    @PostConstruct
    void init() {
        long count = client.count();
        if (count >= MINIMUM) {
            return;
        }
        for (long i = count; i <= MINIMUM; i++) {
            client.register(ClientRegisterRequestFaker.fake());
        }
    }

}
