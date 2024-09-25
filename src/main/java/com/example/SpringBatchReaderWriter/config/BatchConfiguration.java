package com.example.SpringBatchReaderWriter.config;

import com.example.SpringBatchReaderWriter.models.Transaction;
import com.example.SpringBatchReaderWriter.processors.TransactionProcessor;
import com.example.SpringBatchReaderWriter.readers.CsvItemReader;
import com.example.SpringBatchReaderWriter.readers.XmlItemReader;
import com.example.SpringBatchReaderWriter.writers.DatabaseWriter;
import com.example.SpringBatchReaderWriter.writers.XmlWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batchc.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final XmlItemReader xmlItemReader;
    private final CsvItemReader csvItemReader;
    private final TransactionProcessor transactionProcessor;
    private final DatabaseWriter databaseWriter;
    private final XmlWriter xmlWriter;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory,
                              StepBuilderFactory stepBuilderFactory,
                              XmlItemReader xmlItemReader,
                              CsvItemReader csvItemReader,
                              TransactionProcessor transactionProcessor,
                              DatabaseWriter databaseWriter,
                              XmlWriter xmlWriter) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.xmlItemReader = xmlItemReader;
        this.csvItemReader = csvItemReader;
        this.transactionProcessor = transactionProcessor;
        this.databaseWriter = databaseWriter;
        this.xmlWriter = xmlWriter;
    }

    @Bean
    public Job importTransactionJob() {
        return jobBuilderFactory.get("importTransactionJob")
                .incrementer(new RunIdIncrementer())
                .flow(xmlToMySQLStep())
                .next(csvToXmlStep())
                .next(mySQLToXmlStep())
                .end()
                .build();
    }

    @Bean
    public Step xmlToMySQLStep() {
        return stepBuilderFactory.get("xmlToMySQLStep")
                .<Transaction, Transaction>chunk(10)
                .reader(xmlItemReader.xmlItemReader())
                .processor(transactionProcessor)
                .writer(databaseWriter.databaseWriter())
                .build();
    }

    @Bean
    public Step csvToXmlStep() {
        return stepBuilderFactory.get("csvToXmlStep")
                .<Transaction, Transaction>chunk(10)
                .reader(csvItemReader.csvItemReader())
                .processor(transactionProcessor)
                .writer(xmlWriter.xmlWriter())
                .build();
    }

    @Bean
    public Step mySQLToXmlStep() {
        return stepBuilderFactory.get("mySQLToXmlStep")
                .<Transaction, Transaction>chunk(10)
                .reader(databaseReader())
                .writer(xmlWriter.xmlWriter())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Transaction> databaseReader() {
        JdbcCursorItemReader<Transaction> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource());
        reader.setSql("SELECT * FROM transaction");
        reader.setRowMapper(new BeanPropertyRowMapper<>(Transaction.class));
        return reader;
    }
}
