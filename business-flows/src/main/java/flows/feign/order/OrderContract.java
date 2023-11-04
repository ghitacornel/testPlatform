package flows.feign.order;

import commons.model.IdResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface OrderContract {

    @RequestLine("POST /order")
    @Headers("Content-Type: application/json")
    IdResponse create(CreateOrderRequest inputModel);

    @RequestLine("PATCH /order/complete/{id}")
    void complete(@Param("id") Integer id);

}
