package contracts.invoices;

import commons.model.IdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface InvoiceContract {

    @PostMapping(value = "invoice", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    IdResponse create(@RequestBody InvoiceCreateRequest request);

    @PostMapping(value = "invoice/update/order", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@RequestBody UpdateOrderRequest request);

    @PostMapping(value = "invoice/update/client", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@RequestBody UpdateClientRequest request);

    @PostMapping(value = "invoice/update/company", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@RequestBody UpdateCompanyRequest request);

    @PostMapping(value = "invoice/update/product", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@RequestBody UpdateProductRequest request);

    @PostMapping("invoice/complete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void complete(@RequestBody @PathVariable("id") Integer id);

}
