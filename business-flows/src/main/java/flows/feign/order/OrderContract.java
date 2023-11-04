package flows.feign.order;

import commons.model.IdResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface OrderContract {

    @RequestLine("GET /order/{id}")
    OrderDetailsResponse findById(@Param("id") Integer id);

    @RequestLine("POST /order")
    @Headers("Content-Type: application/json")
    IdResponse create(CreateOrderRequest inputModel);

    @RequestLine("PATCH /order/complete/{id}")
    void complete(@Param("id") Integer id);

    @RequestLine("PATCH /order/cancel/{id}")
    void cancel(@Param("id") Integer id);

}
