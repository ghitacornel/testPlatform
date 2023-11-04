package platform.routes.clients.feign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDetailsResponse {

    private Integer id;
    private String name;
    private String cardType;
    private String country;

}
