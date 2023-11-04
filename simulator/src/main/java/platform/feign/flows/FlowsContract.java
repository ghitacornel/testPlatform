package platform.feign.flows;

import commons.model.IdResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import platform.feign.order.CreateOrderRequest;

public interface FlowsContract {

    @RequestLine("POST /camel/order/create")
    @Headers("Content-Type: application/json")
    IdResponse createOrder(CreateOrderRequest inputModel);

    @RequestLine("PATCH /camel/order/complete/{id}")
    void completeOrder(@Param("id") Integer id);

    @RequestLine("PATCH /camel/order/cancel/{id}")
    void cancelOrder(@Param("id") Integer id);

}
