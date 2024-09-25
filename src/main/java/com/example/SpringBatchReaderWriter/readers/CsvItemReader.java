package com.example.SpringBatchReaderWriter.readers;



import com.example.SpringBatchReaderWriter.models.Transaction;
import org.springframework.SpringBatchReaderWriter.item.file.FlatFileItemReader;
import org.springframework.SpringBatchReaderWriter.item.file.mapping.DefaultLineMapper;
import org.springframework.SpringBatchReaderWriter.item.file.transform.DelimitedLineTokenizer;
import org.springframework.SpringBatchReaderWriter.item.file.transform.FieldSetMapper;
import org.springframework.SpringBatchReaderWriter.item.file.transform.LineMapper;
import org.springframework.SpringBatchReaderWriter.item.file.mapping.FieldSetMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class CsvItemReader {

    @Bean
    public FlatFileItemReader<Transaction> csvItemReader() {
        FlatFileItemReader<Transaction> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("data/transactions.csv"));
        reader.setLineMapper(new DefaultLineMapper<Transaction>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("id", "amount");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Transaction>() {{
                setTargetType(Transaction.class);
            }});
        }});
        return reader;
    }
}
