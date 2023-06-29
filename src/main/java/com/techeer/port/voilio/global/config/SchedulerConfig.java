package com.techeer.port.voilio.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {
    private final Job sleeperUserJob;
    private final JobLauncher jobLauncher;

    @Scheduled(cron = "0 00 10 * * ?") // 매일 오전 10시 00분에 실행
    public void runSleeperUserJob() throws Exception {
        jobLauncher.run(sleeperUserJob, new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters());
    }

}
