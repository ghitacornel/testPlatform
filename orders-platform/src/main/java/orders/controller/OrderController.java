package orders.controller;

import lombok.RequiredArgsConstructor;
import orders.controller.model.request.CreateOrderRequest;
import orders.controller.model.response.OrderDetailsResponse;
import orders.service.OrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("count")
    public long count() {
        return service.count();
    }

    @GetMapping(value = "{id}")
    public OrderDetailsResponse findById(@Valid @NotNull @PathVariable(name = "id") Integer id) {
        return service.findById(id);
    }

    @GetMapping(value = "/product/{id}")
    public boolean existsByProductId(@Valid @NotNull @PathVariable(name = "id") Integer id) {
        return service.existsByProductId(id);
    }

    @PostMapping
    public OrderDetailsResponse create(@Valid @RequestBody CreateOrderRequest request) {
        return service.create(request);
    }

    @PatchMapping(value = "/complete/{id}")
    public void complete(@Valid @NotNull @PathVariable(name = "id") Integer id) {
        service.complete(id);
    }

    @DeleteMapping(value = "{id}")
    public void deleteById(@Valid @NotNull @PathVariable(name = "id") Integer id) {
        service.deleteById(id);
    }
}
