package flows.feign.invoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {

    private Integer id;
    private Integer productId;
    private String productName;
    private String productColor;
    private Double productPrice;

}
