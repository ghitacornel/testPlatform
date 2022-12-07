package orders.service.mapper;

import orders.controller.model.response.OrderDetailsResponse;
import orders.repository.entity.Order;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {

    OrderDetailsResponse map(Order data);

}
