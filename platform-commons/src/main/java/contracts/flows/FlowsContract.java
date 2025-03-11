package contracts.flows;

import commons.model.IdResponse;
import contracts.orders.CreateOrderRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

public interface FlowsContract {

    @PostMapping(value = "camel/order/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    IdResponse createOrder(@RequestBody CreateOrderRequest inputModel);

    @PatchMapping("camel/order/complete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void completeOrder(@PathVariable("id") Integer id);

    @PatchMapping("camel/order/cancel/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void cancelOrder(@PathVariable("id") Integer id);

    @DeleteMapping("camel/company/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCompany(@PathVariable("id") Integer id);

    @DeleteMapping("camel/client/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteClient(@PathVariable("id") Integer id);

}
