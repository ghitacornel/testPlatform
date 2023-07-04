package orders.controller.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrderRequest {

    @NotNull
    private Integer clientId;

    @NotNull
    private Integer productId;

    @NotNull
    @Min(1)
    private Integer quantity;

}
