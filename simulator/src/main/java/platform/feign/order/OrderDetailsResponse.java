package platform.feign.order;

import lombok.Data;

@Data
public class OrderDetailsResponse {

    private Integer id;
    private Integer clientId;
    private Integer productId;
    private Integer quantity;
    private OrderStatus status;

}
