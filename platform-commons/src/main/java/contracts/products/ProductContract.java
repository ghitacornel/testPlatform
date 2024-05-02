package contracts.products;

import commons.model.IdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ProductContract {

    @GetMapping("product/count")
    long countAllActive();

    @GetMapping(value = "product", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProductDetailsResponse> findAllActive();

    @GetMapping(value = "product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ProductDetailsResponse findById(@PathVariable("id") Integer id);

    @PostMapping(value = "product/sell", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    IdResponse sell(@RequestBody ProductSellRequest inputModel);

    @PutMapping(value = "product/buy", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    IdResponse buy(@RequestBody ProductBuyRequest inputModel);

    @DeleteMapping("product/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void cancel(@PathVariable("id") Integer id);

    @DeleteMapping("product/company/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void cancelByCompany(@PathVariable("id") Integer id);

    @PatchMapping("product/refill/{id}/{quantity}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void refill(@PathVariable("id") Integer id, @PathVariable("quantity") Integer quantity);

}
