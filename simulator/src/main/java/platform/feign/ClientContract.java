package platform.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "clients-cloud")
public interface ClientContract extends contracts.clients.ClientContract {
}
