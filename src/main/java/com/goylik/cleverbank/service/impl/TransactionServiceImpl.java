package com.goylik.cleverbank.service.impl;

import com.goylik.cleverbank.entity.Account;
import com.goylik.cleverbank.entity.Transaction;
import com.goylik.cleverbank.entity.type.TransactionType;
import com.goylik.cleverbank.repository.exception.DalException;
import com.goylik.cleverbank.repository.impl.TransactionRepository;
import com.goylik.cleverbank.service.TransactionService;
import com.goylik.cleverbank.service.exception.ServiceException;
import lombok.AllArgsConstructor;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * Providing methods to manage transaction-related operations.
 */
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    /**
     * Creates a new transaction and saves it.
     *
     * @param receiver The receiving account for the transaction.
     * @param sender The sending account for the transaction.
     * @param amount The amount involved in the transaction.
     * @param transactionType The type of the transaction.
     * @return The created transaction.
     * @throws ServiceException If an error occurs during transaction creation or saving.
     */
    @Override
    public Transaction create(Account receiver, Account sender, BigDecimal amount, TransactionType transactionType)
            throws ServiceException {
        try {
            String number = UUID.randomUUID().toString();
            Transaction transaction = new Transaction(number, receiver, sender, amount, transactionType, LocalDate.now());
            this.transactionRepository.save(transaction);
            transaction = this.transactionRepository.findByNumber(number);
            saveReceiptToFile(formatReceipt(transaction), "check/receipt" + transaction.getId() + ".txt");
            return transaction;
        } catch (DalException e) {
            throw new ServiceException("Can't create transaction.", e);
        }
    }

    private String formatReceipt(Transaction transaction) {
        String senderBank = (transaction.getSender() != null) ? "|" + " Банк отправителя:" + String.format("%29s", transaction.getSender().getBank().getName()) + " |\n" : "";
        String receiverBank = (transaction.getReceiver() != null) ? "|" + " Банк получателя:" + String.format("%30s", transaction.getReceiver().getBank().getName()) + " |\n" : "";

        String senderAccount = (transaction.getSender() != null) ? "|" + " Счет отправителя:" + String.format("%29s", transaction.getSender().getNumber()) + " |\n" : "";
        String receiverAccount = (transaction.getReceiver() != null) ? "|" + " Счет получателя:" + String.format("%30s", transaction.getReceiver().getNumber()) + " |\n" : "";

        return  "-".repeat(50) + "\n" +
                "|" + " ".repeat(17) + "Банковский чек" + " ".repeat(17) + "|\n" +
                "|" + " Чек:" + String.format("%42s", transaction.getNumber()) + " |\n" +
                "| " + String.format("%10s", transaction.getTransactionDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))) + " ".repeat(37) + "|\n" +
                "|" + " Тип транзакции:" + String.format("%31s", transaction.getTransactionType().getType()) + " |\n" +
                senderBank +
                receiverBank +
                senderAccount +
                receiverAccount +
                "|" + " Сумма:" + String.format("%36.2f BYN", transaction.getAmount()) + " |\n" +
                "|" + "_".repeat(48) + "|\n";
    }

    public void saveReceiptToFile(String formattedReceipt, String filePath) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(formattedReceipt);
            fileWriter.close();
            System.out.println("Банковский чек успешно сохранен в файл: " + filePath);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении банковского чека в файл: " + e.getMessage());
        }
    }

    /**
     * Retrieves a transaction by its ID.
     *
     * @param id The ID of the transaction to retrieve.
     * @return The retrieved transaction.
     * @throws ServiceException If an error occurs while retrieving the transaction.
     */
    @Override
    public Transaction getById(Long id) throws ServiceException {
        try {
            return this.transactionRepository.findById(id);
        } catch (DalException e) {
            throw new ServiceException("Can't find transaction by id.", e);
        }
    }

    /**
     * Retrieves a transaction by its number.
     *
     * @param number The number of the transaction to retrieve.
     * @return The retrieved transaction.
     * @throws ServiceException If an error occurs while retrieving the transaction.
     */
    @Override
    public Transaction getByNumber(String number) throws ServiceException {
        try {
            return this.transactionRepository.findByNumber(number);
        } catch (DalException e) {
            throw new ServiceException("Can't find transaction by number.", e);
        }
    }

    /**
     * Retrieves all transactions.
     *
     * @return A list of all transactions.
     * @throws ServiceException If an error occurs while retrieving all transactions.
     */
    @Override
    public List<Transaction> getAll() throws ServiceException {
        try {
            return this.transactionRepository.findAll();
        } catch (DalException e) {
            throw new ServiceException("Can't find all transactions.", e);
        }
    }

    @Override
    public void deleteAll() throws ServiceException {
        try {
            this.transactionRepository.deleteAll();
        }
        catch (DalException e) {
            throw new ServiceException("Can't delete all transactions.", e);
        }
    }
}

