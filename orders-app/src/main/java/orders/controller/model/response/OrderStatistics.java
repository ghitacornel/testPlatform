package orders.controller.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatistics {

    private long countAll;
    private long countAllNew;
    private long countCancelled;
    private long countCompleted;

}
