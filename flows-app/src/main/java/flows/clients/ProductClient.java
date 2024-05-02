package flows.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "products-cloud")
public interface ProductClient extends contracts.products.ProductContract {
}
