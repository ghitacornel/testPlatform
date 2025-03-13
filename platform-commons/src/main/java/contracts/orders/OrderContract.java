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

    @PostMapping(value = "orders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    IdResponse create(@Valid @RequestBody CreateOrderRequest inputModel);

    @PatchMapping("orders/complete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void complete(@Valid @NotNull @PathVariable("id") Integer id);

    @PatchMapping("orders/cancel/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void cancel(@Valid @NotNull @PathVariable("id") Integer id);

}
