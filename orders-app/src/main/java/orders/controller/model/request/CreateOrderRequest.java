package orders.controller.model.request;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class CreateOrderRequest {

    @NotNull
    private Integer clientId;

    @NotNull
    private Integer productId;

    @NotNull
    @Positive
    private Integer quantity;

}
