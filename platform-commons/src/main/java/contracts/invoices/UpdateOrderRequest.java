package contracts.invoices;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequest {

    @NotNull
    private Integer id;

    @Positive
    private Integer orderQuantity;

    @NotNull
    private Integer clientId;

    @NotNull
    private Integer productId;

}
