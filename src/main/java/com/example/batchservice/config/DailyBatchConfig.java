package com.example.batchservice.config;

import com.example.batchservice.constants.BatchConfigConstants;
import com.example.batchservice.listener.JobCompletionNotificationListener;
import com.example.batchservice.service.BatchService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class DailyBatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final BatchService batchService;

    public DailyBatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager, BatchService batchService) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.batchService = batchService;
    }

    @Bean
    public Job dailyJob(JobCompletionNotificationListener listener, Step dailyStep) {
        return new JobBuilder(BatchConfigConstants.DAILY_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(dailyStep)
                .build();
    }

    @Bean
    public Step dailyStep() {
        return new StepBuilder(BatchConfigConstants.DAILY_STEP_NAME, jobRepository)
                .tasklet(dailyTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Tasklet dailyTasklet() {
        return (contribution, chunkContext) -> {
            batchService.collectAndSaveDailyData();
            return RepeatStatus.FINISHED;
        };
    }
}
