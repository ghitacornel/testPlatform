package orders.clients.company;

import commons.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import orders.clients.RESTClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CompanyClient {

    private final RESTClient restClient;

    public CompanyDto findById(Integer id) {
        return restClient.companies()
                .get()
                .uri("/company/" + id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new BusinessException("Company not found for id " + id)))
                .bodyToMono(CompanyDto.class)
                .block();
    }
}
