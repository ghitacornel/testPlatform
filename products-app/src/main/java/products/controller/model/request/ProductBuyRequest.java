package products.controller.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductBuyRequest {

    @NotNull
    private Integer clientId;

    @NotNull
    private Integer productId;

    @Min(1)
    private int quantity;

}
