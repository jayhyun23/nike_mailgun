package nike.mailgun.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class ThreadPoolInit {

    @Bean(name = "executor")
    public Executor setExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(100);
        executor.setMaxPoolSize(150);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("executor-");
        return executor;
    }
}
