package platform.feign.order;

import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface OrderContract {

    @RequestLine("GET /order")
    List<OrderDetailsResponse> findAllNew();

    @RequestLine("PATCH /order/cancel/{id}")
    void cancelById(@Param("id") Integer id);

}
