package orders.mapper;

import contracts.orders.CreateOrderRequest;
import contracts.orders.OrderDetailsResponse;
import orders.repository.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OrderMapper {

    OrderDetailsResponse map(Order order);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "rejectReason", ignore = true)
    @Mapping(target = "version", ignore = true)
    Order map(CreateOrderRequest request);

}
