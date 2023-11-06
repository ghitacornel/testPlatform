package contracts.products;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSellRequest {

    private String name;
    private String color;
    private Double price;
    private int quantity;
    private Integer companyId;

}
