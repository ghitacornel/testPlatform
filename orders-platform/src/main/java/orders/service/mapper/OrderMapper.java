package orders.service.mapper;

import orders.controller.model.response.OrderDetailsResponse;
import orders.repository.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderMapper {

    @Mapping(source = "", target = "")
    OrderDetailsResponse map(Order data);

}
