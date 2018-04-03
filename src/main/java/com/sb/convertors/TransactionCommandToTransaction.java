package com.sb.convertors;

import com.sb.commands.TransactionCommand;
import com.sb.commands.TransactionWrapper;
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
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

/**
 * Created by Kingsley Kumar on 27/03/2018 at 22:02.
 */
@Slf4j
@Component
public class TransactionCommandToTransaction implements Converter<TransactionCommand, Optional<TransactionWrapper>> {

    @Override
    public Optional<TransactionWrapper> convert(TransactionCommand transactionCommand) {

        ServletRequestAttributes attr = (ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);

        Transaction transaction = new Transaction();

        try {

            String date = transactionCommand.getDate();
            String type = transactionCommand.getType();
            String category = transactionCommand.getCategory();
            String subCategory = transactionCommand.getSubcategory();
            String item = transactionCommand.getItem();
            String value = transactionCommand.getValue();

            if (StringUtils.isBlank(date) || StringUtils.isBlank(category) || StringUtils.isBlank(item) || StringUtils.isBlank(
                    value) || !isNumeric(value)) {

                log.error("Unable to convert transaction command to transaction. " +
                                  "One of the these input (date, category, item, value) is wrong.");

                return Optional.empty();
            }

            String typeEncrypted = CryptoUtils.getEncryptedDataFromOriginal(Constants.TX_KEY, type);
            String categoryEncrypted = CryptoUtils.getEncryptedDataFromOriginal(Constants.TX_KEY, category);
            String subCategoryEncrypted = subCategory;

            if (!StringUtils.isBlank(subCategory)) {
                subCategoryEncrypted = CryptoUtils.getEncryptedDataFromOriginal(Constants.TX_KEY, subCategory);
            }

            String itemEncrypted = CryptoUtils.getEncryptedDataFromOriginal(Constants.TX_KEY, item);
            String valueEncrypted = CryptoUtils.getEncryptedDataFromOriginal(Constants.TX_KEY, value);

            String budget = transactionCommand.getBudget();
            String budgetEncrypted = budget;

            if (!StringUtils.isBlank(budget)) {

                budgetEncrypted = CryptoUtils.getEncryptedDataFromOriginal(Constants.TX_KEY, budget);
            }

            TransactionContent transactionContent = new TransactionContent();

            transactionContent.setType(typeEncrypted);
            transactionContent.setCategory(categoryEncrypted);
            transactionContent.setSubcategory(subCategoryEncrypted);
            transactionContent.setItem(itemEncrypted);
            transactionContent.setValue(valueEncrypted);
            transactionContent.setId(Integer.valueOf(transactionCommand.getId()));
            transactionContent.setBy("");
            transactionContent.setBudget(budgetEncrypted);

            DateTimeFormatter inputDateTimeFormatter = CommonUtils.getDateTimeFormatter(session);

            Date txDate = CommonUtils.getDate(date, inputDateTimeFormatter);

            transaction.setDate(txDate);
            transaction.setTransactions(Collections.singletonList(transactionContent));


        } catch (CryptoException e ) {
            log.error("Exception while encrypting information from transaction command. " + e.getMessage());
        }

        return Optional.of(new TransactionWrapper(transaction, transactionCommand.isDelete()));
    }

    private boolean isNumeric(String value) {

        boolean numeric = true;
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException e) {
            log.error("Exception while formatting number. " + e.getMessage());
            numeric = false;
        }

        return numeric;
    }
}
