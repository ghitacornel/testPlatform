package flows.service;

import contracts.clients.ClientDetailsResponse;
import contracts.companies.CompanyDetailsResponse;
import contracts.invoices.*;
import contracts.orders.OrderDetailsResponse;
import contracts.products.ProductDetailsResponse;
import flows.clients.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceClient invoiceClient;
    private final OrderClient orderClient;
    private final ClientClient clientClient;
    private final CompanyClient companyClient;
    private final ProductClient productClient;

    @Async
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void complete(Integer id) {

        OrderDetailsResponse orderDetails = orderClient.findById(id);

        invoiceClient.create(InvoiceCreateRequest.builder()
                .orderId(orderDetails.getId())
                .build());
        log.info("Invoice created {}", orderDetails.getId());

        try {

            invoiceClient.update(UpdateOrderRequest.builder()
                    .id(id)
                    .clientId(orderDetails.getClientId())
                    .productId(orderDetails.getProductId())
                    .orderQuantity(orderDetails.getQuantity())
                    .build());

            ProductDetailsResponse productDetails = productClient.findById(orderDetails.getProductId());
            invoiceClient.update(UpdateProductRequest.builder()
                    .id(id)
                    .productId(productDetails.getId())
                    .productName(productDetails.getName())
                    .productColor(productDetails.getColor())
                    .productPrice(productDetails.getPrice())
                    .companyId(productDetails.getCompanyId())
                    .build());

            ClientDetailsResponse clientDetails = clientClient.findById(orderDetails.getClientId());
            invoiceClient.update(UpdateClientRequest.builder()
                    .id(id)
                    .clientId(clientDetails.getId())
                    .clientName(clientDetails.getName())
                    .clientCardType(clientDetails.getCardType())
                    .clientCountry(clientDetails.getCountry())
                    .build());

            CompanyDetailsResponse companyDetails = companyClient.findById(productDetails.getCompanyId());
            invoiceClient.update(UpdateCompanyRequest.builder()
                    .id(id)
                    .companyId(companyDetails.getId())
                    .companyName(companyDetails.getName())
                    .companyUrl(companyDetails.getUrl())
                    .companyIndustry(companyDetails.getIndustry())
                    .companyCountry(companyDetails.getCountry())
                    .build());

            invoiceClient.complete(id);
            orderClient.invoice(id);

        } catch (Exception e) {
            invoiceClient.error(id);
            throw e;
        }

    }

}

