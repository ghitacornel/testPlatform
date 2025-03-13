package contracts.products;

import commons.model.IdResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
public interface ProductContract {

    @GetMapping(value = "products", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProductDetailsResponse> findAllActive();

    @GetMapping("products/count")
    long countAllActive();

    @GetMapping(value = "products/company/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProductDetailsResponse> findAllActiveForCompany(@Valid @NotNull @PathVariable("id") Integer id);

    @GetMapping(value = "products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ProductDetailsResponse findById(@Valid @NotNull @PathVariable("id") Integer id);

    @PostMapping(value = "products/sell", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    IdResponse sell(@Valid @RequestBody ProductSellRequest inputModel);

    @PutMapping(value = "products/buy", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void buy(@Valid @RequestBody ProductBuyRequest inputModel);

    @PatchMapping("products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void cancel(@Valid @NotNull @PathVariable("id") Integer id);

    @PatchMapping("products/company/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void cancelByCompany(@Valid @NotNull @PathVariable("id") Integer id);

    @PatchMapping("products/refill/{id}/{quantity}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void refill(@Valid @NotNull @PathVariable("id") Integer id, @Valid @NotNull @Positive @PathVariable("quantity") Integer quantity);

    @GetMapping(value = "products/ids/consumed", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Integer> findConsumedIds();

    @GetMapping(value = "products/ids/cancelled", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Integer> findCancelledIds();

    @GetMapping(value = "products/ids/active", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Integer> findActiveIds();

    @DeleteMapping("products/{id}")
    void delete(@Valid @NotNull @PathVariable("id") Integer id);

    @GetMapping(value = "products/exists/company/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    boolean existsByCompanyId(@Valid @NotNull @PathVariable("id") Integer id);

}
