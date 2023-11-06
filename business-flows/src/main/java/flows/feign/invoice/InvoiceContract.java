package flows.feign.invoice;

import feign.RequestLine;

public interface InvoiceContract {

    @RequestLine("PATCH /invoice/update/order")
    void update(UpdateOrderRequest request);

    @RequestLine("PATCH /invoice/update/client")
    void update(UpdateClientRequest request);

    @RequestLine("PATCH /invoice/update/company")
    void update(UpdateCompanyRequest request);

    @RequestLine("PATCH /invoice/update/product")
    void update(UpdateProductRequest request);

}
