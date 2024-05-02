package platform.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "flows-cloud")
public interface FlowsContract extends contracts.flows.FlowsContract {
}
