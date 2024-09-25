package com.example.SpringBatchReaderWriter.writers;


import com.example.SpringBatchReaderWriter.models.Transaction;
import org.springframework.SpringBatchReaderWriter.item.xml.StaxEventItemWriter;
import org.springframework.SpringBatchReaderWriter.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

@Component
public class XmlWriter {

    @Bean
    public StaxEventItemWriter<Transaction> xmlWriter() {
        StaxEventItemWriter<Transaction> writer = new StaxEventItemWriter<>();
        writer.setResource(new FileSystemResource("src/main/resources/data/processed_transactions.xml"));
        writer.setMarshaller(new Jaxb2Marshaller() {{
            setClasses(Transaction.class);
        }});
        writer.setRootTagName("transactions");
        return writer;
    }
}
