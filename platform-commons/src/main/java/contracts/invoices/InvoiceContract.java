package contracts.invoices;

import commons.model.IdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

public interface InvoiceContract {

    @PostMapping(value = "invoice", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    IdResponse create(@RequestBody InvoiceCreateRequest request);

    @PatchMapping(value = "invoice/update/order", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@RequestBody UpdateOrderRequest request);

    @PatchMapping(value = "invoice/update/client", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@RequestBody UpdateClientRequest request);

    @PatchMapping(value = "invoice/update/company", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@RequestBody UpdateCompanyRequest request);

    @PatchMapping(value = "invoice/update/product", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@RequestBody UpdateProductRequest request);

    @PatchMapping("invoice/complete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void complete(@RequestBody @PathVariable("id") Integer id);

}
