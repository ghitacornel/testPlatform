package orders.controller.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class CreateOrderRequest {

    @NotNull
    private Integer clientId;

    @NotNull
    private Integer productId;

    @NotNull
    @Positive
    private Integer productQuantity;

}
