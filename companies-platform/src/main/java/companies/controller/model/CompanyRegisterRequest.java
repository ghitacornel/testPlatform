package companies.controller.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CompanyRegisterRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String url;

    @NotBlank
    private String industry;

}
