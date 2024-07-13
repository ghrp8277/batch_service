package com.example.batchservice.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    private final JobLauncher jobLauncher;
    private final Job dailyJob;

    public SchedulerConfig(JobLauncher jobLauncher, Job dailyJob) {
        this.jobLauncher = jobLauncher;
        this.dailyJob = dailyJob;
    }

    @Scheduled(cron = "0 0 0 * * ?") // 매일 00시에 실행
    public void performDailyJob() throws Exception {
        jobLauncher.run(dailyJob, new JobParametersBuilder().toJobParameters());
    }
}
