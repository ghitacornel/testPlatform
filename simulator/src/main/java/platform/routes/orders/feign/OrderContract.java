package platform.routes.orders.feign;

import commons.model.IdResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface OrderContract {

    @RequestLine("GET /order/count")
    long countAllNew();

    @RequestLine("GET /order")
    List<OrderDetailsResponse> findAllNew();

    @RequestLine("GET /order/{id}")
    OrderDetailsResponse findById(@Param("id") Integer id);

    @RequestLine("POST /order")
    @Headers("Content-Type: application/json")
    IdResponse create(CreateOrderRequest inputModel);

    @RequestLine("PATCH /order/{id}")
    void completeById(@Param("id") Integer id);

    @RequestLine("DELETE /order/{id}")
    void deleteById(@Param("id") Integer id);

}
