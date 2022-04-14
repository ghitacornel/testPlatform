package products.clients.company;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import products.clients.RESTClient;

@Component
@RequiredArgsConstructor
public class CompanyClient {

    private final RESTClient restClient;

    public CompanyDto findByName(String name) {
        return restClient.companies()
                .get()
                .uri("/company/" + name)
                .retrieve()
                .bodyToMono(CompanyDto.class)
                .block();
    }
}
