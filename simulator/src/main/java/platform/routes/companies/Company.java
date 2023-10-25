package platform.routes.companies;

import lombok.*;

@Data
@EqualsAndHashCode(of = {"id", "name"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    private Integer id;
    private String name;
    private String url;
    private String industry;
    private String country;

}
