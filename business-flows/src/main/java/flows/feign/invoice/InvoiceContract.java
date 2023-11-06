package flows.feign.invoice;

import feign.Headers;
import feign.RequestLine;

public interface InvoiceContract {

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
