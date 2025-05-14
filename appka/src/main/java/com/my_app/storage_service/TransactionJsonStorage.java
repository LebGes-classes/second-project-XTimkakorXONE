package com.my_app.storage_service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.my_app.entities.customer.transaction.Transaction;
import com.my_app.utils.JsonStorageService;

public class TransactionJsonStorage extends JsonStorageService<Transaction> {

    public TransactionJsonStorage() {
        super("storage/Transaction.json", Transaction.class);
    }

    public void saveTransaction(Transaction transaction) throws IOException {
        if (transaction == null) {
            throw new IllegalArgumentException("Транзакция не может быть null");
        }
        List<Transaction> transactions = readFile();
        transactions.add(transaction);
        writeFile(transactions);
    }

    public List<Transaction> getCustomerTransactions(Integer customerId) throws IOException {
        if (customerId == null) {
            throw new IllegalArgumentException("ID клиента не может быть null");
        }
        return readFile().stream()
            .filter(t -> Objects.equals(t.getCustomerId(), customerId))
            .collect(Collectors.toList());
    }
    
    public Integer getNextTransactionId() throws IOException {
        List<Transaction> transactions = readFile();
        if (transactions.isEmpty()) {
            return 1;
        }
        return transactions.stream()
                .mapToInt(Transaction::getId)
                .max()
                .orElse(0) + 1;
    }
}
