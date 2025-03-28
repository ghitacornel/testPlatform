package contracts.companies;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRegisterRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String country;

    @NotBlank
    private String industry;

    @NotBlank
    private String url;

}
