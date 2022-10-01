package products.clients.order;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
public class CreateOrderRequest {

    @NotNull
    private Integer clientId;

    @NotNull
    private Integer productId;

    @NotNull
    @Column(nullable = false)
    private Integer productQuantity;

}
