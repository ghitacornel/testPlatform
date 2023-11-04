package flows.feign.product;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface ProductContract {

    @RequestLine("PUT /product/buy")
    @Headers("Content-Type: application/json")
    void buy(ProductBuyRequest inputModel);

    @RequestLine("PATCH /product/refill/{id}/{quantity}")
    void refill(@Param("id") Integer id, @Param("quantity") Integer quantity);

}
