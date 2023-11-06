package invoices.controller.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDetails {

    private Integer id;
    private Integer orderQuantity;

    private Integer clientId;
    private String clientName;
    private String clientCardType;
    private String clientCountry;

    private Integer companyId;
    private String companyName;
    private String companyUrl;
    private String companyIndustry;
    private String companyCountry;

    private Integer productId;
    private String productName;
    private String productColor;
    private Double productPrice;

}
