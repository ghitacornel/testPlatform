package contracts.invoices;

import commons.model.IdResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
public interface InvoiceContract {

    @GetMapping("invoice")
    List<InvoiceDetails> findAll();

    @GetMapping("invoice/count")
    long count();

    @PostMapping(value = "invoice", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    IdResponse create(@Valid @RequestBody InvoiceCreateRequest request);

    @PatchMapping(value = "invoice/update/order", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@Valid @RequestBody UpdateOrderRequest request);

    @PatchMapping(value = "invoice/update/client", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@Valid @RequestBody UpdateClientRequest request);

    @PatchMapping(value = "invoice/update/company", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@Valid @RequestBody UpdateCompanyRequest request);

    @PatchMapping(value = "invoice/update/product", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@Valid @RequestBody UpdateProductRequest request);

    @PatchMapping("invoice/complete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void complete(@Valid @NotNull @PathVariable("id") Integer id);

}
