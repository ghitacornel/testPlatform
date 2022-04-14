package clients.controllers.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ClientRegisterRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String cardType;

}
