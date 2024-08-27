package com.example.batchservice.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchController {
    private final JobLauncher jobLauncher;
    private final Job dailyJob;
    private final Job initialJob;

    @Autowired
    public BatchController(JobLauncher jobLauncher, Job dailyJob, Job initialJob) {
        this.jobLauncher = jobLauncher;
        this.dailyJob = dailyJob;
        this.initialJob = initialJob;
    }

    @GetMapping("/run-daliy-batch")
    public ResponseEntity<String> runDailyBatch() {
        try {
            jobLauncher.run(dailyJob, new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters());
            return ResponseEntity.ok("Daily batch job has been invoked");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Daily batch job failed");
        }
    }

    @GetMapping("/run-batch")
    public ResponseEntity<String> runBatch() {
        try {
            jobLauncher.run(initialJob, new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters());
            return ResponseEntity.ok("Initial batch job has been invoked");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Initial batch job failed");
        }
    }
}

