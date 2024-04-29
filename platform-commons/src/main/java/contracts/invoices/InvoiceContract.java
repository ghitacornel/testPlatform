package contracts.invoices;

import commons.model.IdResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface InvoiceContract {

    @RequestLine("POST /invoice")
    @Headers("Content-Type: application/json")
    IdResponse create(InvoiceCreateRequest request);

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

    @RequestLine("PATCH /complete/{id}")
    void complete(@Param("id") Integer id);

}
