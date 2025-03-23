package flows.config;

import contracts.exceptions.ExceptionErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
class FeignClientConfiguration {

    @Bean
    ExceptionErrorDecoder errorDecoder() {
        return new ExceptionErrorDecoder();
    }

}