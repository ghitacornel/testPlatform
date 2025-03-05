package contracts.products;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSellRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String color;

    @NotNull
    @Min(1)
    private Double price;

    @Min(100)
    private int quantity;

    @NotNull
    private Integer companyId;

}
