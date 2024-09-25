package com.example.SpringBatchReaderWriter.processors;


import com.example.SpringBatchReaderWriter.model.Transaction;
import org.springframework.SpringBatchReaderWriter.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TransactionProcessor implements ItemProcessor<Transaction, Transaction> {
    @Override
    public Transaction process(Transaction transaction) {
        // Simple transformation logic, e.g., apply discount
        transaction.setAmount(transaction.getAmount() * 0.9); // Applying a 10% discount
        return transaction;
    }
}
