package com.example.SpringBatchReaderWriter.writers;



import com.example.SpringBatchReaderWriter.models.Transaction;
import org.springframework.SpringBatchReaderWriter.item.database.JdbcBatchItemWriter;
import org.springframework.SpringBatchReaderWriter.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class DatabaseWriter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcBatchItemWriter<Transaction> databaseWriter() {
        JdbcBatchItemWriter<Transaction> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("INSERT INTO transaction (amount) VALUES (:amount)");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return writer;
    }
}
