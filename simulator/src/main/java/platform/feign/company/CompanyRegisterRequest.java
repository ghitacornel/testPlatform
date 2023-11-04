package platform.feign.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRegisterRequest {

    private String name;
    private String url;
    private String industry;
    private String country;

}
