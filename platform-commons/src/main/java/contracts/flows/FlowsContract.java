package contracts.flows;

import commons.model.IdResponse;
import contracts.orders.CreateOrderRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

public interface FlowsContract {

    @PostMapping(value = "camel/orders/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    IdResponse createOrder(@RequestBody CreateOrderRequest inputModel);

    @PatchMapping("camel/orders/complete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void completeOrder(@PathVariable("id") Integer id);

    @PatchMapping("camel/orders/cancel/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void cancelOrder(@PathVariable("id") Integer id);

    @DeleteMapping("camel/companies/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCompany(@PathVariable("id") Integer id);

    @DeleteMapping("camel/clients/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteClient(@PathVariable("id") Integer id);

}
