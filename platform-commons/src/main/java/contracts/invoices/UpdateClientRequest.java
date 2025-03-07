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
public class UpdateClientRequest {

    @NotNull
    private Integer id;

    private Integer clientId;
    private String clientName;
    private String clientCardType;
    private String clientCountry;

}
