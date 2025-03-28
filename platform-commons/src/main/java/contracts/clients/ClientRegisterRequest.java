package contracts.clients;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRegisterRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String country;

    @NotBlank
    private String cardType;

}
