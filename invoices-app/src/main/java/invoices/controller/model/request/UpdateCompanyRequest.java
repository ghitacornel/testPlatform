package invoices.controller.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCompanyRequest {

    @NotNull
    private Integer id;

    private Integer companyId;
    private String companyName;
    private String companyUrl;
    private String companyIndustry;
    private String companyCountry;

}
