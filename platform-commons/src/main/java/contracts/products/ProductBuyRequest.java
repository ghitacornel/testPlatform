package contracts.products;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductBuyRequest {

    @NotNull
    private Integer clientId;

    @NotNull
    private Integer productId;

    @Min(1)
    private int quantity;

}
