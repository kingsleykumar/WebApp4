package com.sb.repositories;

import com.sb.domain.Transaction;
import com.sb.domain.TransactionContent;

import java.util.Date;
import java.util.List;

/**
 * Created by Kingsley Kumar on 29/03/2018 at 14:41.
 */
public interface CustomTransactionRepository {

    List<Transaction> retrieveTransactionsBetweenDates(String userId, String category, Date fromDate, Date toDate);

    boolean deleteTransactions(String userId, Date date, List<Integer> txIds);

    boolean updateTransactions(String userId, Date date, List<TransactionContent> transactionContents);

    boolean insertTransactions(String userId, Date date, List<TransactionContent> transactionContents);

    boolean anyTransactionExistsForCategory(String userId, String categoryName);

    boolean anyTransactionExistsForBudget(String userId, String budgetName);
}
