package flows.feign.product;

import feign.Headers;
import feign.RequestLine;

public interface ProductContract {

    @RequestLine("PUT /product/buy")
    @Headers("Content-Type: application/json")
    void buy(ProductBuyRequest inputModel);

}
