package flows.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "clients-cloud")
public interface ClientClient extends contracts.clients.ClientContract {
}
