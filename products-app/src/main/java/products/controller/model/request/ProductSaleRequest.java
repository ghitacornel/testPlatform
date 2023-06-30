package products.controller.model.request;

import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

    @NotNull
    private Integer companyId;

}
