package invoices.repository.entity;

import commons.model.Identifiable;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
@Getter
@Setter
public class Invoice extends Identifiable {

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

    private Integer orderId;
    private Integer orderQuantity;

}
