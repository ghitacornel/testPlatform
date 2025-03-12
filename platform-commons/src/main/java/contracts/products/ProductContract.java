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

    @GetMapping("product/count")
    long countAllActive();

    @GetMapping(value = "product", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProductDetailsResponse> findAllActive();

    @GetMapping(value = "product/company/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProductDetailsResponse> findAllActiveForCompany(@Valid @NotNull @PathVariable("id") Integer id);

    @GetMapping(value = "product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ProductDetailsResponse findById(@Valid @NotNull @PathVariable("id") Integer id);

    @PostMapping(value = "product/sell", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    IdResponse sell(@Valid @RequestBody ProductSellRequest inputModel);

    @PutMapping(value = "product/buy", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void buy(@Valid @RequestBody ProductBuyRequest inputModel);

    @DeleteMapping("product/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void cancel(@Valid @NotNull @PathVariable("id") Integer id);

    @DeleteMapping("product/company/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void cancelByCompany(@Valid @NotNull @PathVariable("id") Integer id);

    @PatchMapping("product/refill/{id}/{quantity}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void refill(@Valid @NotNull @PathVariable("id") Integer id, @Valid @NotNull @Positive @PathVariable("quantity") Integer quantity);

}
