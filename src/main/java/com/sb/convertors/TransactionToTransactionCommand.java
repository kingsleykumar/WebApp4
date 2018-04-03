package com.sb.convertors;

import com.sb.commands.TransactionCommand;
import com.sb.domain.Transaction;
import com.sb.domain.TransactionContent;
import com.sb.services.utils.CommonUtils;
import com.sb.services.utils.Constants;
import com.sb.services.utils.CryptoException;
import com.sb.services.utils.CryptoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Kingsley Kumar on 27/03/2018 at 22:48.
 */
@Slf4j
@Component
public class TransactionToTransactionCommand implements Converter<Transaction, List<TransactionCommand>> {


    @Override
    public List<TransactionCommand> convert(Transaction transaction) {

        ServletRequestAttributes attr = (ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);

        DateTimeFormatter inputDateTimeFormatter = CommonUtils.getDateTimeFormatter(session);

        Date date = transaction.getDate();

        String dateStr = CommonUtils.getDateStr(date, inputDateTimeFormatter);

        List<TransactionContent> transactionContents = transaction.getTransactions();

        List<TransactionCommand> transactionCommands = IntStream.range(0, transactionContents.size())
                                                                .mapToObj(i -> getTransactionCommandFromTransactionContent(
                                                                        dateStr,
                                                                        transactionContents.get(i)))
                                                                .filter(Optional::isPresent)
                                                                .map(Optional::get)
                                                                .collect(Collectors.toList());

        return transactionCommands;
    }


    private Optional<TransactionCommand> getTransactionCommandFromTransactionContent(String dateStr,
                                                                                     TransactionContent transactionContent) {

        TransactionCommand transactionCommand = new TransactionCommand();

        try {
            String type = transactionContent.getType();
            String typeOriginal = CryptoUtils.getOriginalDataFromEncrypted(Constants.TX_KEY, type);

            String category = transactionContent.getCategory();
            String categoryOriginal = CryptoUtils.getOriginalDataFromEncrypted(Constants.TX_KEY, category);

            String subCategory = transactionContent.getSubcategory();
            String subCategoryOriginal = subCategory;

            if (!StringUtils.isBlank(subCategory)) {
                subCategoryOriginal = CryptoUtils.getOriginalDataFromEncrypted(Constants.TX_KEY, subCategory);
            }

            String item = transactionContent.getItem();
            String itemOriginal = CryptoUtils.getOriginalDataFromEncrypted(Constants.TX_KEY, item);

            String value = transactionContent.getValue();
            String valueOriginal = CryptoUtils.getOriginalDataFromEncrypted(Constants.TX_KEY, value);

            String budget = transactionContent.getBudget();
            String budgetOriginal = budget;
            if (!StringUtils.isBlank(budget))
                budgetOriginal = CryptoUtils.getOriginalDataFromEncrypted(Constants.TX_KEY, budget);

            transactionCommand.setDate(dateStr);
            transactionCommand.setType(typeOriginal);
            transactionCommand.setCategory(categoryOriginal);
            transactionCommand.setSubcategory(subCategoryOriginal);
            transactionCommand.setItem(itemOriginal);
            transactionCommand.setValue(valueOriginal);
            transactionCommand.setId(String.valueOf(transactionContent.getId()));
            transactionCommand.setBudget(budgetOriginal);

        } catch (CryptoException e) {
            log.error("Exception while converting transaction content to transaction command.", e.getMessage());
            return Optional.empty();
        }

        return Optional.of(transactionCommand);
    }

}
