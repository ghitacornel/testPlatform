package flows.feign;

import com.netflix.discovery.EurekaClient;
import contracts.products.ProductContract;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProductFeignConfiguration {

    @Bean
    ProductContract productContract(EurekaClient eurekaClient) {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(ProductContract.class))
                .logLevel(Logger.Level.FULL)
                .target(ProductContract.class, eurekaClient.getApplication("products-cloud").getInstances().getFirst().getHomePageUrl());
    }
}
