package platform.feign;

import com.netflix.discovery.EurekaClient;
import contracts.flows.FlowsContract;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class FlowsFeignConfiguration {

    @Bean
    FlowsContract flowsContract(EurekaClient eurekaClient) {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(FlowsContract.class))
                .logLevel(Logger.Level.FULL)
                .target(FlowsContract.class, eurekaClient.getApplication("flows-cloud").getInstances().getFirst().getHomePageUrl());
    }
}
