package products.controller.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ProductBuyRequest {

    @NotBlank
    private String userName;

    @NotNull
    private Integer productId;

    @NotNull
    @Positive
    private Integer quantity;

}
