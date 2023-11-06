package contracts.invoices;

import commons.model.IdResponse;
import feign.Headers;
import feign.RequestLine;

public interface InvoiceContract {

    @RequestLine("POST /invoice")
    @Headers("Content-Type: application/json")
    IdResponse createInvoice(InvoiceCreateRequest request);

    @RequestLine("PATCH /invoice/update/order")
    @Headers("Content-Type: application/json")
    void update(UpdateOrderRequest request);

    @RequestLine("PATCH /invoice/update/client")
    @Headers("Content-Type: application/json")
    void update(UpdateClientRequest request);

    @RequestLine("PATCH /invoice/update/company")
    @Headers("Content-Type: application/json")
    void update(UpdateCompanyRequest request);

    @RequestLine("PATCH /invoice/update/product")
    @Headers("Content-Type: application/json")
    void update(UpdateProductRequest request);

}
