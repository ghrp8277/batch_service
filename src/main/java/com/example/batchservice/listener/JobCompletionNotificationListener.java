package com.example.batchservice.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private Instant startTime;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = Instant.now();
        logger.info("Job 시작...");
        logger.info("Job Name: {}", jobExecution.getJobInstance().getJobName());
        logger.info("Job Parameters: {}", jobExecution.getJobParameters());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Duration jobDuration = Duration.between(startTime, Instant.now());
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("Job 완료!");
            logger.info("Job Duration: {} ms", jobDuration.toMillis());
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            logger.error("Job 실패!");
            logger.error("Job Duration: {} ms", jobDuration.toMillis());
        }

        jobExecution.getStepExecutions().forEach(stepExecution -> {
            logger.info("Step Name: {}", stepExecution.getStepName());
            logger.info("Read Count: {}", stepExecution.getReadCount());
            logger.info("Write Count: {}", stepExecution.getWriteCount());
            logger.info("Commit Count: {}", stepExecution.getCommitCount());
        });
    }
}
