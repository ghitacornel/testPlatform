package products.clients.company;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import products.clients.RESTClient;

@Component
@RequiredArgsConstructor
public class CompanyClient {

    private final RESTClient restClient;

    public CompanyDetailsDto findById(Integer id) {
        return restClient.companies()
                .get()
                .uri("/company/" + id)
                .retrieve()
                .bodyToMono(CompanyDetailsDto.class)
                .block();
    }
}
