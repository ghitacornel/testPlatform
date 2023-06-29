package companies.controller.model.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class CompanyRegisterRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String url;

    @NotBlank
    private String industry;

    @NotBlank
    private String country;

}
