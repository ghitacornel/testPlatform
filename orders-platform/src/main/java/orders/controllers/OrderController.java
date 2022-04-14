package orders.controllers;

import lombok.RequiredArgsConstructor;
import orders.controllers.models.CreateOrderRequest;
import orders.controllers.models.OrderDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import orders.services.OrderService;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Validated
public class OrderController {
    private final OrderService service;

    @GetMapping("/all")
    public List<OrderDto> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "{id}")
    public OrderDto findById(@Validated @NotNull @PathVariable(name = "id") Integer id) {
        return service.findById(id);
    }

    @PostMapping
    public OrderDto create(@Validated @RequestBody CreateOrderRequest request) {
        return service.create(request);
    }
}
