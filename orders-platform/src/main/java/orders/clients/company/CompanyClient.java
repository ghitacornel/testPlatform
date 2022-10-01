package orders.clients.company;

import lombok.RequiredArgsConstructor;
import orders.clients.RESTClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyClient {

    private final RESTClient restClient;

    public CompanyDto findById(Integer id) {
        return restClient.companies()
                .get()
                .uri("/company/" + id)
                .retrieve()
                .bodyToMono(CompanyDto.class)
                .block();
    }
}
