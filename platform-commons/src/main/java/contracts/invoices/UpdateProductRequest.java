package contracts.invoices;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {

    @NotNull
    private Integer id;

    private Integer productId;
    private Integer companyId;
    private String productName;
    private String productColor;
    private Double productPrice;

}
