package platform.feign.flows;

import commons.model.IdResponse;
import feign.Headers;
import feign.RequestLine;
import platform.feign.order.CreateOrderRequest;

public interface FlowsContract {

    @RequestLine("POST /camel/order/create")
    @Headers("Content-Type: application/json")
    IdResponse createOrder(CreateOrderRequest inputModel);

}
