package contracts.products;

import commons.model.IdResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface ProductContract {

    @RequestLine("GET /product/count")
    long countAllActive();

    @RequestLine("GET /product")
    List<ProductDetailsResponse> findAllActive();

    @RequestLine("GET /product/{id}")
    ProductDetailsResponse findById(@Param("id") Integer id);

    @RequestLine("POST /product/sell")
    @Headers("Content-Type: application/json")
    IdResponse sell(ProductSellRequest inputModel);

    @RequestLine("PUT /product/buy")
    @Headers("Content-Type: application/json")
    IdResponse buy(ProductBuyRequest inputModel);

    @RequestLine("DELETE /product/{id}")
    void cancel(@Param("id") Integer id);

}
