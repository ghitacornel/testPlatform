package platform.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import platform.common.RESTClient;
import platform.company.model.Company;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final RESTClient restClient;

    public List<Company> findAll() {
        return restClient.companies()
                .get()
                .uri("/company/all")
                .retrieve()
                .bodyToFlux(Company.class)
                .collectList()
                .block();
    }

    public Company register(Company item) {
        return restClient.companies()
                .post()
                .uri("/company/register")
                .bodyValue(item)
                .retrieve()
                .bodyToMono(Company.class)
                .block();
    }

    public void unregister(Company item) {
        restClient.companies()
                .delete()
                .uri("/company/" + item.getName())
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
