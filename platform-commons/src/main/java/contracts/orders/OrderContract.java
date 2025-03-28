package contracts.orders;

import commons.model.IdResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
public interface OrderContract {

    @GetMapping(value = "orders", produces = MediaType.APPLICATION_JSON_VALUE)
    List<OrderDetailsResponse> findAllNew();

    @GetMapping(value = "orders/completed/ids", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Integer> findCompletedIds();

    @GetMapping(value = "orders/sent/ids", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Integer> findSentIds();

    @GetMapping(value = "orders/invoiced/ids", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Integer> findInvoicedIds();

    @GetMapping(value = "orders/new/ids", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Integer> findNewIds();

    @GetMapping(value = "orders/rejected/ids", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Integer> findRejectedIds();

    @GetMapping(value = "orders/client/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<OrderDetailsResponse> findAllNewForClientId(@Valid @NotNull @PathVariable("id") Integer id);

    @GetMapping(value = "orders/company/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<OrderDetailsResponse> findAllNewForProductId(@Valid @NotNull @PathVariable("id") Integer id);

    @GetMapping("orders/count")
    long countAllNew();

    @GetMapping(value = "orders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    OrderDetailsResponse findById(@Valid @NotNull @PathVariable("id") Integer id);

    @GetMapping("orders/exists/product/{id}")
    boolean existsByProductId(@Valid @NotNull @PathVariable(name = "id") Integer id);

    @GetMapping("orders/exists/client/{id}")
    boolean existsByClientId(@Valid @NotNull @PathVariable(name = "id") Integer id);

    @PostMapping(value = "orders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    IdResponse create(@Valid @RequestBody CreateOrderRequest inputModel);

    @PatchMapping("orders/complete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void complete(@Valid @NotNull @PathVariable("id") Integer id);

    @PatchMapping("orders/invoice/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void invoice(@Valid @NotNull @PathVariable("id") Integer id);

    @PatchMapping("orders/invoice-sent/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void markAsSentToInvoice(@Valid @NotNull @PathVariable("id") Integer id);

    @PatchMapping("orders/cancel/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void cancel(@Valid @NotNull @PathVariable("id") Integer id);

    @PatchMapping("orders/cancel/product/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void cancelByProductId(@Valid @NotNull @PathVariable("id") Integer id);

    @PatchMapping(value = "orders/reject", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void reject(@Valid @RequestBody OrderRejectRequest request);

}
