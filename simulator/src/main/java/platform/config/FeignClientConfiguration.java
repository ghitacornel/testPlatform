package platform.config;

import contracts.exceptions.ExceptionErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class FeignClientConfiguration {

    @Bean
    ExceptionErrorDecoder errorDecoder() {
        return new ExceptionErrorDecoder();
    }

}