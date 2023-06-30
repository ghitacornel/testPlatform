package company.service;

import commons.model.IdResponse;
import company.common.RESTClient;
import company.model.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private static final String COMPANY_URL = "/company";
    private final RESTClient restClient;

    public List<Company> findAll() {
        return restClient.companies()
                .get()
                .uri(COMPANY_URL)
                .retrieve()
                .bodyToFlux(Company.class)
                .collectList()
                .block();
    }

    public Long count() {
        return restClient.companies()
                .get()
                .uri(COMPANY_URL + "/count")
                .retrieve()
                .bodyToMono(Long.class)
                .block();
    }

    public IdResponse register(Company item) {
        return restClient.companies()
                .post()
                .uri(COMPANY_URL)
                .bodyValue(item)
                .retrieve()
                .bodyToMono(IdResponse.class)
                .block();
    }

    public void unregister(Company item) {
        restClient.companies()
                .delete()
                .uri(COMPANY_URL + "/" + item.getId())
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
