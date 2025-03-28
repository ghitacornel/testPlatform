package contracts.flows;

import commons.model.IdResponse;
import contracts.orders.CreateOrderRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

public interface FlowsContract {

    @PostMapping(value = "flow/orders/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    IdResponse createOrder(@RequestBody CreateOrderRequest inputModel);

    @PatchMapping("flow/orders/cancel/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void cancelOrder(@PathVariable("id") Integer id);

    @DeleteMapping("flow/companies/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCompany(@PathVariable("id") Integer id);

    @DeleteMapping("flow/clients/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteClient(@PathVariable("id") Integer id);

    @DeleteMapping("flow/products/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void cancelProduct(@PathVariable("id") Integer id);

}
