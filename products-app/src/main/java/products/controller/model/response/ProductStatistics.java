package products.controller.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductStatistics {

    private long countAll;
    private long countActive;
    private long countCancelled;
    private long countConsumed;

}
