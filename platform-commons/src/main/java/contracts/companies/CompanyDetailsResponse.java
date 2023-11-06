package contracts.companies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDetailsResponse {

    private Integer id;
    private String name;
    private String url;
    private String industry;
    private String country;

}
