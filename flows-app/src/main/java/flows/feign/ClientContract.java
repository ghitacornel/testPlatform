package flows.feign;

import contracts.clients.ClientDetailsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clients-cloud", url = "http://localhost")
public interface ClientContract {

    @GetMapping(value = "/client/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDetailsResponse findById(@PathVariable("id") Integer id);

}
