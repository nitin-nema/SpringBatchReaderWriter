package com.example.SpringBatchReaderWriter.readers;



import com.example.SpringBatchReaderWriter.models.Transaction;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

public class XmlItemReader {

    @Bean
    public StaxEventItemReader<Transaction> xmlItemReader() {
        return new StaxEventItemReaderBuilder<Transaction>()
                .name("transactionXmlReader")
                .resource(new ClassPathResource("data/transactions.xml"))
                .addFragmentRootElements("transaction")
                .unmarshaller(new Jaxb2Marshaller() {{
                    setClasses(Transaction.class);
                }})
                .build();
    }
}

//Stax - Streaming API for Xml