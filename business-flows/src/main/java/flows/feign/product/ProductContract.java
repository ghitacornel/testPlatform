package flows.feign.product;

import commons.model.IdResponse;
import feign.Headers;
import feign.RequestLine;

public interface ProductContract {

    @RequestLine("PUT /product/buy")
    @Headers("Content-Type: application/json")
    IdResponse buy(ProductBuyRequest inputModel);

}
