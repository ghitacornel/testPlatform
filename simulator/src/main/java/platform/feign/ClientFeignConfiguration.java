package platform.feign;

import com.netflix.discovery.EurekaClient;
import contracts.clients.ClientContract;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ClientFeignConfiguration {

    @Bean
    ClientContract clientContract(EurekaClient eurekaClient) {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(ClientContract.class))
                .logLevel(Logger.Level.FULL)
                .target(ClientContract.class, eurekaClient.getApplication("clients-cloud").getInstances().getFirst().getHomePageUrl());
    }

}
