package products.clients.company;

import commons.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import products.clients.RESTClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CompanyClient {

    private final RESTClient restClient;

    public CompanyDetailsDto findById(Integer id) {
        return restClient.companies()
                .get()
                .uri("/company/" + id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new BusinessException("Company not found for id " + id)))
                .bodyToMono(CompanyDetailsDto.class)
                .block();
    }
}
