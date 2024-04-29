package invoices.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Getter
@Setter
public class Invoice {

    @Id
    private Integer id;// Invoice ID = Order ID

    private Integer orderQuantity;

    private Integer clientId;
    private String clientName;
    private String clientCardType;
    private String clientCountry;

    private Integer productId;
    private String productName;
    private String productColor;
    private Double productPrice;

    private Integer companyId;
    private String companyName;
    private String companyUrl;
    private String companyIndustry;
    private String companyCountry;

    @NotNull
    @Enumerated
    private InvoiceStatus status = InvoiceStatus.NEW;

    public void complete(){
        this.status = InvoiceStatus.COMPLETED;
    }

}
