package platform.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "orders-cloud")
public interface OrderClient extends contracts.orders.OrderContract {
}
