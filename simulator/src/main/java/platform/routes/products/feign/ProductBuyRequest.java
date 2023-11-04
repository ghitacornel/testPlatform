package platform.routes.products.feign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductBuyRequest {

    private Integer clientId;
    private Integer productId;
    private int quantity;

}
