package com.sb.services;

import com.sb.services.containers.ResultMessage;
import com.sb.commands.TransactionCommand;
import com.sb.commands.TransactionCommandWrapper;
import com.sb.commands.TransactionWrapper;
import com.sb.convertors.TransactionCommandToTransaction;
import com.sb.convertors.TransactionToTransactionCommand;
import com.sb.domain.Transaction;
import com.sb.domain.TransactionContent;
import com.sb.repositories.TransactionRepository;
import com.sb.services.utils.CommonUtils;
import com.sb.services.utils.Constants;
import com.sb.services.utils.CryptoException;
import com.sb.services.utils.CryptoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Created by Kingsley Kumar on 28/03/2018 at 16:33.
 */
@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private TransactionCommandToTransaction transactionCommandToTransaction;
    private TransactionToTransactionCommand transactionToTransactionCommand;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  TransactionCommandToTransaction transactionCommandToTransaction,
                                  TransactionToTransactionCommand transactionToTransactionCommand) {

        this.transactionRepository = transactionRepository;
        this.transactionCommandToTransaction = transactionCommandToTransaction;
        this.transactionToTransactionCommand = transactionToTransactionCommand;
    }

    private BiFunction<Map<Date, List<TransactionWrapper>>, String, Void> addFunction = (mapDateWithTransactions, userId) -> {

        mapDateWithTransactions.keySet()
                               .stream()
                               .forEach(date -> addOrInsertTransactionsOnDate(userId,
                                                                              date,
                                                                              mapDateWithTransactions.get(date)));
        return null;
    };

    private BiFunction<Map<Date, List<TransactionWrapper>>, String, Void> updateFunction = (mapDateWithTransactions, userId) -> {

        mapDateWithTransactions.keySet()
                               .stream()
                               .forEach(date -> updateTransactionsOnDate(userId,
                                                                         date,
                                                                         mapDateWithTransactions.get(date)));
        return null;
    };

    @Override
    public Optional<ResultMessage> addTransactions(TransactionCommandWrapper transactionCommandWrapper,
                                                   HttpSession session) {

        return handleAddUpdateOperation(transactionCommandWrapper,
                                        session,
                                        "No transaction is available for adding into DB.", addFunction);
    }


    @Override
    public Optional<ResultMessage> updateTransactions(TransactionCommandWrapper transactionCommandWrapper,
                                                      HttpSession session) {

        return handleAddUpdateOperation(transactionCommandWrapper,
                                        session,
                                        "No transaction is available for update operation.", updateFunction);
    }

    private Optional<ResultMessage> handleAddUpdateOperation(TransactionCommandWrapper transactionCommandWrapper,
                                                             HttpSession session,
                                                             String errorMessage,
                                                             BiFunction<Map<Date, List<TransactionWrapper>>, String, Void> function) {


        List<TransactionCommand> transactionCommands = transactionCommandWrapper.getTransactionCommands();

        List<TransactionWrapper> transactions = transactionCommands.stream()
                                                                   .map(transactionCommand -> transactionCommandToTransaction
                                                                           .convert(transactionCommand))
                                                                   .filter(Optional::isPresent)
                                                                   .map(Optional::get)
                                                                   .collect(Collectors.toList());
        if (transactions.size() > 0) {

            String userId = String.valueOf(session.getAttribute("userid"));

            Map<Date, List<TransactionWrapper>> mapDateWithTransactions = groupTransactionsByDate(transactions);

            log.debug("mapDateWithTransactions = " + mapDateWithTransactions);

            function.apply(mapDateWithTransactions, userId);

        } else {

            log.info(errorMessage);

            return Optional.of(new ResultMessage(false, errorMessage));
        }

        return Optional.empty();
    }

    private void updateTransactionsOnDate(String userId, Date date, List<TransactionWrapper> transactionList) {

        List<Integer> txIdsForDeletion = new ArrayList<>();
        List<TransactionContent> txListForUpdate = new ArrayList<>();

        for (TransactionWrapper transactionWrapper : transactionList) {

            Transaction transaction = transactionWrapper.getTransaction();

            if (transactionWrapper.isDelete()) {

                txIdsForDeletion.add(transaction.getTransactions().get(0).getId());

            } else {

                Optional<Transaction> transactionOptional = transactionRepository.findByUserIdAndDate(userId, date);

                if (transactionOptional.isPresent()) {

                    TransactionContent userSubmittedTx = transaction.getTransactions().get(0);

                    Transaction transactionInDB = transactionOptional.get();

                    Optional<TransactionContent> matchingTxInDB =
                            transactionInDB.getTransactions()
                                           .stream()
                                           .filter(e -> e.getId().intValue() == userSubmittedTx.getId().intValue())
                                           .findFirst();

                    boolean hasUserChangedTheTx = true;

                    if (matchingTxInDB.isPresent()) {

                        TransactionContent txInDB = matchingTxInDB.get();

                        hasUserChangedTheTx = !areBothTransactionSame(txInDB, userSubmittedTx);
                    }

                    log.info("hasUserChangedTheTx = [" + hasUserChangedTheTx + "] for tx id : " + userSubmittedTx.getId() + " on date = " + date);

                    if (hasUserChangedTheTx) {

                        txListForUpdate.add(userSubmittedTx);
                    }

                } else {

                    log.error("No transaction exist to update on the date " + date + " , for user " + userId);
                }
            }
        }

//        log.info("txIdsForDeletion.size() = " + txIdsForDeletion.size());
//        log.info("txListForUpdate.size() = " + txListForUpdate.size());

        int noOfTxToBeDeleted = txIdsForDeletion.size();

        if (noOfTxToBeDeleted > 0) {

            transactionRepository.deleteTransactions(userId, date, txIdsForDeletion);

            log.info(txIdsForDeletion.size() + " transaction(s) have been removed on date " + date + " for the user " + userId);

            removeDocumentIfNoTxExists(userId, date, transactionList.size(), noOfTxToBeDeleted);
        }

        if (txListForUpdate.size() > 0) {

            transactionRepository.updateTransactions(userId, date, txListForUpdate);

            log.info(txListForUpdate.size() + " transaction(s) have been updated on date " + date + " for the user " + userId);
        }
    }

    private void removeDocumentIfNoTxExists(String userId,
                                            Date date,
                                            int userSubmittedTxSize,
                                            int noOfTxToBeDeleted) {

        // Check If the document on that date has any transactions left. If no transactions left, then remove that document.
        if (noOfTxToBeDeleted == userSubmittedTxSize) {

            Optional<Transaction> transactionOptional = transactionRepository.findByUserIdAndDate(userId, date);

            if (transactionOptional.isPresent()) {

                Transaction transaction = transactionOptional.get();

                if (transaction.getTransactions().size() == 0) {

                    transactionRepository.deleteById(transaction.get_id());
                }
            }
        }
    }

    private boolean areBothTransactionSame(TransactionContent txInDB, TransactionContent userSubmittedTx) {

        boolean typeEquals = StringUtils.equals(txInDB.getType(), userSubmittedTx.getType());
        boolean categoryEquals = StringUtils.equals(txInDB.getCategory(), userSubmittedTx.getCategory());
        boolean subCategoryEquals = StringUtils.equals(txInDB.getSubcategory(), userSubmittedTx.getSubcategory());
        boolean itemEquals = StringUtils.equals(txInDB.getItem(), userSubmittedTx.getItem());
        boolean valueEquals = StringUtils.equals(txInDB.getValue(), userSubmittedTx.getValue());
        boolean budgetEquals = StringUtils.equals(txInDB.getBudget(), userSubmittedTx.getBudget());
        boolean byEquals = StringUtils.equals(txInDB.getBy(), userSubmittedTx.getBy());
        boolean idEquals = txInDB.getId().intValue() == userSubmittedTx.getId().intValue();

        return (typeEquals && categoryEquals && subCategoryEquals && itemEquals && valueEquals && budgetEquals &&
                byEquals && idEquals);
    }

    private void addOrInsertTransactionsOnDate(String userId, Date date, List<TransactionWrapper> transactionList) {

        Optional<Transaction> transactionOptional = transactionRepository.findByUserIdAndDate(userId, date);

//        Transaction transaction;

        if (transactionOptional.isPresent()) {

            Transaction transactionInDB = transactionOptional.get();

            // find max id in current transaction on given date
            Integer maxId = transactionInDB.getTransactions()
                                           .stream()
                                           .max(Comparator.comparing(TransactionContent::getId))
                                           .get()
                                           .getId();

            log.debug("maxId = " + maxId);

            // update id on transactionList
            AtomicInteger maxIdAtomic = updateIdOnTransaction(transactionList, maxId);
            log.debug("maxIdAtomic = " + maxIdAtomic);

            // merge transactionList into transaction from db.
            List<TransactionContent> newTransactions = transactionList.stream()
                                                                      .flatMap(wrapper -> wrapper.getTransaction()
                                                                                                 .getTransactions()
                                                                                                 .stream())
                                                                      .collect(Collectors.toList());
//            transactionInDB.getTransactions().addAll(newTransactions);

            log.info("transaction before saving = " + newTransactions);

            transactionRepository.insertTransactions(userId, date, newTransactions);

        } else {

            // update id on transactionList
            AtomicInteger maxIdAtomic = updateIdOnTransaction(transactionList, 0);
            log.debug("maxIdAtomic = " + maxIdAtomic);

            Transaction transaction = new Transaction();
            transaction.setUserId(userId);
            transaction.setDate(date);

            // add transactionList into transaction
            List<TransactionContent> newTransactions = transactionList.stream()
                                                                      .flatMap(wrapper -> wrapper.getTransaction()
                                                                                                 .getTransactions()
                                                                                                 .stream())
                                                                      .collect(Collectors.toList());
            transaction.setTransactions(newTransactions);

            log.debug("transaction before saving = " + transaction);

            transactionRepository.save(transaction);
        }

    }

    @Override
    public List<TransactionCommand> retrieveTransactions(HttpServletRequest req, HttpSession session) {

        String userId = String.valueOf(session.getAttribute("userid"));

        String fromDateStr = req.getParameter("dateFrom");
        String toDateStr = req.getParameter("dateTo");
        String category = req.getParameter("selectedCategory");

        if (StringUtils.isBlank(fromDateStr)) {

            log.error("From date is not selected.");

            return new ArrayList<>(0);
        }

        if (StringUtils.isBlank(toDateStr)) {

            log.error("To date is not selected.");

            return new ArrayList<>(0);
        }

        DateTimeFormatter userInputDateTimeFormatter = CommonUtils.getDateTimeFormatter(session);

        Date fromDate = CommonUtils.getDate(fromDateStr, userInputDateTimeFormatter);

        Date toDate = CommonUtils.getDate(toDateStr, userInputDateTimeFormatter);

        if (fromDate.after(toDate)) {

            fromDate = toDate;
        }

        if (!StringUtils.isBlank(category)) {

            try {
                category = CryptoUtils.getEncryptedDataFromOriginal(Constants.TX_KEY, category);
            } catch (CryptoException e) {
                log.error("Exception while encrypting category string. " + e.getMessage());
            }
        }

        List<Transaction> transactions = transactionRepository.retrieveTransactionsBetweenDates(userId,
                                                                                                category,
                                                                                                fromDate,
                                                                                                toDate);

        List<TransactionCommand> transactionCommands = new ArrayList<>();

        for (Transaction transaction : transactions) {

            List<TransactionCommand> commands = transactionToTransactionCommand.convert(transaction);

            transactionCommands.addAll(commands);
        }

        log.debug("transactionCommands.size() = " + transactionCommands.size());

        return transactionCommands;
    }

    private AtomicInteger updateIdOnTransaction(List<TransactionWrapper> transactionList, Integer maxId) {

        AtomicInteger maxIdAtomic = new AtomicInteger(maxId);

        transactionList.stream()
                       .map(wrapper -> wrapper.getTransaction().getTransactions())
                       .flatMap(transactionContents -> transactionContents.stream())
                       .forEach(transactionContent -> transactionContent.setId(maxIdAtomic.incrementAndGet()));

        return maxIdAtomic;
    }

    private Map<Date, List<TransactionWrapper>> groupTransactionsByDate(List<TransactionWrapper> transactions) {

        Map<Date, List<TransactionWrapper>> mapDateWithTransactions = new TreeMap<>();

        for (TransactionWrapper transaction : transactions) {

            Date date = transaction.getTransaction().getDate();

            List<TransactionWrapper> txList = mapDateWithTransactions.computeIfAbsent(date, key -> new ArrayList<>());

            txList.add(transaction);
        }

        return mapDateWithTransactions;
    }
}
