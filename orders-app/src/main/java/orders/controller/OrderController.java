package orders.controller;

import commons.model.IdResponse;
import lombok.RequiredArgsConstructor;
import orders.controller.model.request.CreateOrderRequest;
import orders.controller.model.response.OrderDetailsResponse;
import orders.service.OrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService service;

    @GetMapping
    public List<OrderDetailsResponse> findAllNew() {
        return service.findAllNew();
    }

    @GetMapping("count")
    public long countAllNew() {
        return service.countAllNew();
    }

    @GetMapping("{id}")
    public OrderDetailsResponse findById(@Valid @NotNull @PathVariable(name = "id") Integer id) {
        return service.findById(id);
    }

    @GetMapping("product/{id}")
    public boolean existsByProductId(@Valid @NotNull @PathVariable(name = "id") Integer id) {
        return service.existsByProductId(id);
    }

    @PostMapping
    public IdResponse create(@Valid @RequestBody CreateOrderRequest request) {
        return service.create(request);
    }

    @PatchMapping("complete/{id}")
    public void completeById(@Valid @NotNull @PathVariable(name = "id") Integer id) {
        service.completeById(id);
    }

    @PatchMapping("cancel/{id}")
    public void cancelById(@Valid @NotNull @PathVariable(name = "id") Integer id) {
        service.cancelById(id);
    }

    @DeleteMapping("{id}")
    public void deleteById(@Valid @NotNull @PathVariable(name = "id") Integer id) {
        service.deleteById(id);
    }
}
