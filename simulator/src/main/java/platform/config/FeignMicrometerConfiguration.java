package platform.config;

import feign.Capability;
import feign.micrometer.MicrometerCapability;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class FeignMicrometerConfiguration {

    @Bean
    Capability capability(MeterRegistry registry) {
        return new MicrometerCapability(registry);
    }

}