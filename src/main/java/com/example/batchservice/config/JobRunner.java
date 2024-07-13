package com.example.batchservice.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobRunner implements CommandLineRunner {

    private final JobLauncher jobLauncher;
    private final Job initialJob;

    @Autowired
    public JobRunner(JobLauncher jobLauncher, Job initialJob) {
        this.jobLauncher = jobLauncher;
        this.initialJob = initialJob;
    }

    @Override
    public void run(String... args) throws Exception {
        jobLauncher.run(initialJob, new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters());
    }
}