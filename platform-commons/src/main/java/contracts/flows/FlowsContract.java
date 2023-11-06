package contracts.flows;

import commons.model.IdResponse;
import contracts.orders.CreateOrderRequest;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface FlowsContract {

    @RequestLine("POST /camel/order/create")
    @Headers("Content-Type: application/json")
    IdResponse createOrder(CreateOrderRequest inputModel);

    @RequestLine("PATCH /camel/order/complete/{id}")
    void completeOrder(@Param("id") Integer id);

    @RequestLine("PATCH /camel/order/cancel/{id}")
    void cancelOrder(@Param("id") Integer id);

    @RequestLine("DELETE /camel/company/delete/{id}")
    void deleteCompany(Integer body);

}
