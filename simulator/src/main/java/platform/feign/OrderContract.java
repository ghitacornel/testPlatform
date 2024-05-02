package platform.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "orders-cloud")
public interface OrderContract extends contracts.orders.OrderContract {
}
