package products.controllers.models;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductSaleRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String color;

    @NotNull
    @Min(1)
    private Double price;

    @NotNull
    @Min(100)
    private Integer quantity;

    @NotBlank
    private String companyName;

}
