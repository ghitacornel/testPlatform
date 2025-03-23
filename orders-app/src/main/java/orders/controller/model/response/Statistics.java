package orders.controller.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Statistics {

    private long countAll;
    private long countNew;
    private long countCompleted;
    private long countSentForInvoice;
    private long countInvoiced;
    private long countCancelled;
    private long countRejected;

}
