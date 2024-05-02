package platform.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "flows-cloud")
public interface FlowsClient extends contracts.flows.FlowsContract {
}
