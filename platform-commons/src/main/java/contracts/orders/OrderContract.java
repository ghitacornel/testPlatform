package contracts.orders;

import commons.model.IdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface OrderContract {

    @GetMapping(value = "order", produces = MediaType.APPLICATION_JSON_VALUE)
    List<OrderDetailsResponse> findAllNew();

    @GetMapping(value = "order/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    OrderDetailsResponse findById(@PathVariable("id") Integer id);

    @PostMapping(value = "order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    IdResponse create(@RequestBody CreateOrderRequest inputModel);

    @PatchMapping("order/complete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void complete(@PathVariable("id") Integer id);

    @PatchMapping("order/cancel/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void cancel(@PathVariable("id") Integer id);

}
