package flows.service;

import commons.exceptions.BusinessException;
import commons.exceptions.RestTechnicalException;
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

        OrderDetailsResponse orderDetails;
        try {
            orderDetails = orderClient.findById(id);
        } catch (BusinessException e) {
            log.error("business error fetching order to complete {} {}", id, e.getMessage());
            return;
        }

        InvoiceCreateRequest invoiceCreateRequest = InvoiceCreateRequest.builder()
                .orderId(orderDetails.getId())
                .build();
        try {
            invoiceClient.create(invoiceCreateRequest);
            log.info("Invoice created {}", orderDetails.getId());
        } catch (BusinessException e) {
            log.error("business error creating invoice for order {} {}", id, e.getMessage());
            return;
        } catch (RestTechnicalException e) {
            log.error("rest tech error creating invoice for order {} {}", id, e.getMessage());
            return;
        } catch (Exception e) {
            log.error("error creating invoice for order {} {}", id, e.getMessage(), e);
            return;
        }

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

        } catch (BusinessException e) {
            log.error("business error completing order {} {}", id, e.getMessage());
            errorInvoice(id, e.getMessage());
        } catch (Exception e) {
            errorInvoice(id, e.getMessage());
            throw e;
        }

    }

    private void errorInvoice(Integer id, String message) {
        try {
            invoiceClient.error(id, message);
        } catch (BusinessException e) {
            log.error("business error marking invoice as error {} {}", id, e.getMessage());
        }
    }

}

