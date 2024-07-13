package com.example.batchservice.config;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final BatchService batchService;

    @Value("file:./schema-mysql.sql")
    private Resource batchSchema;

    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager, BatchService batchService) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.batchService = batchService;
    }

    @Bean
    public DataSourceInitializer batchDataSourceInitializer(DataSource dataSource) {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(batchSchema);
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator);
        return initializer;
    }

    @Bean
    public Job initialJob(JobCompletionNotificationListener listener, Step initialStep, Step calculateIndicatorsStep) {
        return new JobBuilder("initialJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(initialStep)
                .next(calculateIndicatorsStep)
                .build();
    }

    @Bean
    public Step initialStep() {
        return new StepBuilder("initialStep", jobRepository)
                .tasklet(initialTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Tasklet initialTasklet() {
        return (contribution, chunkContext) -> {
            batchService.collectAndSaveInitialData();
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step calculateIndicatorsStep() {
        return new StepBuilder("calculateIndicatorsStep", jobRepository)
                .tasklet(calculateIndicatorsTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Tasklet calculateIndicatorsTasklet() {
        return (contribution, chunkContext) -> {
            batchService.calculateIndicatorsForAllStocks();
            return RepeatStatus.FINISHED;
        };
    }
}
