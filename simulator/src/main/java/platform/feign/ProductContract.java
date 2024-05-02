package platform.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "products-cloud")
public interface ProductContract extends contracts.products.ProductContract {
}
