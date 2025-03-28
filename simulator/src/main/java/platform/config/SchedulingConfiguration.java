package platform.config;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Slf4j
@Configuration
@EnableScheduling
class SchedulingConfiguration implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(20);
        taskScheduler.setThreadNamePrefix("SchedulingThread");
        taskScheduler.setErrorHandler(t -> {
            if (t instanceof FeignException e) {
                log.error("feign error: {}", e.getMessage());
                return;
            }
            throw new RuntimeException(t);
        });
        taskScheduler.initialize();

        taskRegistrar.setTaskScheduler(taskScheduler);
    }

}