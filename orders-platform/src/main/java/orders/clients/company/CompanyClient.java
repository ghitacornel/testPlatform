package orders.clients.company;

import lombok.RequiredArgsConstructor;
import orders.clients.RESTClient;
import orders.exception.CompanyNotFoundException;
import org.springframework.http.HttpStatus;
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
                .onStatus(httpStatus -> httpStatus == HttpStatus.NOT_FOUND, clientResponse -> {
                    throw new CompanyNotFoundException(id);
                })
                .bodyToMono(CompanyDto.class)
                .block();
    }
}
