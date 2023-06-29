package clients.controller.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class ClientRegisterRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String cardType;

    @NotBlank
    private String country;

}
