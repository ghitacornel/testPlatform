package orders.service.mapper;

import orders.controller.model.request.CreateOrderRequest;
import orders.controller.model.response.OrderDetailsResponse;
import orders.repository.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderMapper {

    OrderDetailsResponse map(Order order);

    @Mapping(target = "status", ignore = true)
    Order map(CreateOrderRequest request);

}
