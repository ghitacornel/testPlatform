package companies.controller.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
