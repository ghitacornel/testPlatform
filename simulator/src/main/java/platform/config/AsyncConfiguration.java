package platform.config;

import commons.exceptions.BusinessException;
import commons.exceptions.RestTechnicalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Slf4j
@Configuration
@EnableAsync
class AsyncConfiguration implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(50);
        executor.setThreadNamePrefix("SimulatorAsyncThread-");
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (e, method, params) -> {
            if (e instanceof BusinessException) {
                log.error("Async error: {} {}", method, e.getMessage());
            } else if (e instanceof RestTechnicalException) {
                log.error("Async error: {} {}", method, e.getMessage());
            } else {
                log.error("Async error: {}", method, e);
            }
        };
    }

}