package flows.feign.order;

import commons.model.IdResponse;
import feign.Headers;
import feign.RequestLine;

public interface OrderContract {

    @RequestLine("POST /order")
    @Headers("Content-Type: application/json")
    IdResponse create(CreateOrderRequest inputModel);

}
