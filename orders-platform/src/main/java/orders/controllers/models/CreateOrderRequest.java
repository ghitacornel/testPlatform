package orders.controllers.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class CreateOrderRequest {

    @NotBlank
    private String userName;

    @NotNull
    private Integer productId;

    @NotNull
    @Positive
    private Double productPrice;

    @NotNull
    @Positive
    private Integer productQuantity;

    @NotBlank
    private String companyName;

}
