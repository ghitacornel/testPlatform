package orders.controller.model.response;

import lombok.Data;
import orders.repository.entity.OrderStatus;

@Data
public class OrderDetailsResponse {

    private Integer id;
    private Integer clientId;
    private Integer productId;
    private Integer quantity;
    private OrderStatus status;

}
