package orders.mapper;

import contracts.orders.CreateOrderRequest;
import contracts.orders.OrderDetailsResponse;
import orders.repository.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDetailsResponse map(Order order);

    @Mapping(target = "status", ignore = true)
    Order map(CreateOrderRequest request);

}
