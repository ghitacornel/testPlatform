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

    @GetMapping("invoices")
    List<InvoiceDetails> findAll();

    @GetMapping("invoices/count")
    long count();

    @PostMapping(value = "invoices", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    IdResponse create(@Valid @RequestBody InvoiceCreateRequest request);

    @PatchMapping(value = "invoices/update/order", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@Valid @RequestBody UpdateOrderRequest request);

    @PatchMapping(value = "invoices/update/client", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@Valid @RequestBody UpdateClientRequest request);

    @PatchMapping(value = "invoices/update/company", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@Valid @RequestBody UpdateCompanyRequest request);

    @PatchMapping(value = "invoices/update/product", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@Valid @RequestBody UpdateProductRequest request);

    @PatchMapping("invoices/complete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void complete(@Valid @NotNull @PathVariable("id") Integer id);

    @GetMapping("invoices/exists/order/{id}")
    boolean existsByOrderId(@Valid @NotNull @PathVariable("id") Integer id);

    @GetMapping("invoices/exists/client/{id}")
    boolean existsByClientId(@Valid @NotNull @PathVariable("id") Integer id);

}
