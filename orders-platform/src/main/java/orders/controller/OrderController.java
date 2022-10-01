package orders.controller;

import lombok.RequiredArgsConstructor;
import orders.controller.model.request.CreateOrderRequest;
import orders.controller.model.response.OrderDetailsResponse;
import orders.service.OrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Validated
public class OrderController {
    private final OrderService service;

    @GetMapping
    public List<OrderDetailsResponse> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "{id}")
    public OrderDetailsResponse findById(@Validated @NotNull @PathVariable(name = "id") Integer id) {
        return service.findById(id);
    }

    @PostMapping
    public OrderDetailsResponse create(@Validated @RequestBody CreateOrderRequest request) {
        return service.create(request);
    }
}
