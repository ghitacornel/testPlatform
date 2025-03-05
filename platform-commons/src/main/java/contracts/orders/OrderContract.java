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

    @GetMapping(value = "order", produces = MediaType.APPLICATION_JSON_VALUE)
    List<OrderDetailsResponse> findAllNew();

    @GetMapping("count")
    long countAllNew();

    @GetMapping(value = "order/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    OrderDetailsResponse findById(@Valid @NotNull @PathVariable("id") Integer id);

    @GetMapping("product/{id}")
    boolean existsByProductId(@Valid @NotNull @PathVariable(name = "id") Integer id);

    @PostMapping(value = "order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    IdResponse create(@Valid @RequestBody CreateOrderRequest inputModel);

    @PatchMapping("order/complete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void complete(@Valid @NotNull @PathVariable("id") Integer id);

    @PatchMapping("order/cancel/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void cancel(@Valid @NotNull @PathVariable("id") Integer id);

    @DeleteMapping("{id}")
    void deleteById(@Valid @NotNull @PathVariable(name = "id") Integer id);

}
