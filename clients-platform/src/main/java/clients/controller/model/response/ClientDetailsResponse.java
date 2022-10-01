package clients.controller.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDetailsResponse {

    private Integer id;
    private String name;
    private String cardType;

}
