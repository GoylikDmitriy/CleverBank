package com.goylik.cleverbank.repository.impl;

import com.goylik.cleverbank.entity.Account;
import com.goylik.cleverbank.entity.Transaction;
import com.goylik.cleverbank.entity.type.TransactionType;
import com.goylik.cleverbank.repository.BaseRepository;
import com.goylik.cleverbank.repository.config.impl.TransactionQueryConfig;
import com.goylik.cleverbank.repository.exception.DalException;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class TransactionRepository extends BaseRepository<Transaction> {
    private final AccountRepository accountRepository;

    private final String SQL_INSERT_TRANSACTION = "INSERT INTO cleverbank.transaction " +
            "(number, receiver_id, sender_id, amount, transaction_type, transaction_date) VALUES (?,?,?,?,?,?)";
    private final String SQL_SELECT_BY_NUMBER = "SELECT * FROM cleverbank.transaction WHERE number=?";

    public Transaction findByNumber(String number) throws DalException {
        try {
            Transaction transaction = null;
            List<Transaction> transactions = executeQuery(SQL_SELECT_BY_NUMBER, s -> s.setString(1, number));
            if (!transactions.isEmpty()) {
                transaction = transactions.get(0);
            }

            return transaction;
        }
        catch (SQLException e) {
            throw new DalException("Exception while selecting transaction by number.", e);

        }
    }

    @Override
    protected Transaction create(ResultSet resultSet) throws DalException {
        try {
            Long id = resultSet.getLong(1);
            String number = resultSet.getString(2);
            BigDecimal amount = resultSet.getBigDecimal(5);
            TransactionType transactionType = TransactionType.toType(resultSet.getString(6));
            LocalDate transactionDate = resultSet.getDate(7).toLocalDate();

            Account receiver = null;
            Account sender = null;
            Long receiverId = resultSet.getLong(3);
            Long senderId = resultSet.getLong(4);
            if (receiverId >= 0) {
                receiver = this.accountRepository.findById(receiverId);
            }

            if (senderId >= 0) {
                sender = this.accountRepository.findById(senderId);
            }

            Transaction transaction = new Transaction(number, receiver, sender, amount, transactionType, transactionDate);
            transaction.setId(id);
            return transaction;
        }
        catch (SQLException e) {
            throw new DalException("Exception while creating transaction.", e);
        }
    }

    @Override
    protected String getTableName() {
        return "cleverbank.transaction";
    }

    @Override
    public Transaction save(Transaction entity) throws DalException {
        try {
            Long id = executeUpdate(SQL_INSERT_TRANSACTION, new TransactionQueryConfig(entity));
            entity.setId(id);
            return entity;
        }
        catch (SQLException e) {
            throw new DalException("Exception while inserting transaction.", e);
        }
    }
}
