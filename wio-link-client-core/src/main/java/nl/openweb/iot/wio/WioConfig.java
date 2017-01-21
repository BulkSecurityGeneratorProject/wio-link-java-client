package nl.openweb.iot.wio;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class WioConfig {

    @Bean(destroyMethod = "shutdown")
    public TaskScheduler taskScheduler(WioSettings wioSettings) {
        final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(wioSettings.getTaskSchedulerPoolSize());
        taskScheduler.setWaitForTasksToCompleteOnShutdown(false);
        taskScheduler.setAwaitTerminationSeconds(10);
        taskScheduler.initialize();
        return taskScheduler;
    }
}
