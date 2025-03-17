package platform.init;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import platform.clients.CompanyClient;
import platform.fakers.CompanyRegisterRequestFaker;

import static platform.simulators.CompanyService.MINIMUM;

@Component
@RequiredArgsConstructor
class CompaniesSetup {

    private final CompanyClient client;

    @PostConstruct
    void init() {
        long count = client.count();
        if (count >= MINIMUM) {
            return;
        }
        for (long i = count; i <= MINIMUM; i++) {
            client.register(CompanyRegisterRequestFaker.fake());
        }
    }

}
