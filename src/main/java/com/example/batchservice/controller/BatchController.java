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

    @Autowired
    public BatchController(JobLauncher jobLauncher, Job dailyJob) {
        this.jobLauncher = jobLauncher;
        this.dailyJob = dailyJob;
    }

    @GetMapping("/run-batch")
    public ResponseEntity<String> runBatch() {
        try {
            jobLauncher.run(dailyJob, new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters());
            return ResponseEntity.ok("Batch job has been invoked");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Batch job failed");
        }
    }
}

