package clients.controller.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ClientRegisterRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String cardType;

}
