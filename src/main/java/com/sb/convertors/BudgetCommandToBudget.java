package com.sb.convertors;

import com.sb.commands.BudgetCommand;
import com.sb.commands.CategoryBudgetCommand;
import com.sb.domain.Budget;
import com.sb.domain.CategoryBudget;
import com.sb.services.utils.CommonUtils;
import com.sb.services.utils.CryptoException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sb.services.utils.Constants.BUDGET_KEY;
import static com.sb.services.utils.CryptoUtils.getEncryptedDataFromOriginal;

/**
 * Created by Kingsley Kumar on 25/03/2018 at 22:41.
 */
@Slf4j
@Setter
@Component
public class BudgetCommandToBudget implements Converter<BudgetCommand, Budget> {

    @Override
    public Budget convert(BudgetCommand budgetCommand) {

        ServletRequestAttributes attr = (ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);

        Budget budget = new Budget();

        try {
            String name = budgetCommand.getName();
            String nameEncrypted = getEncryptedDataFromOriginal(BUDGET_KEY, name);

            String amount = budgetCommand.getAmount();
            String amountEncrypted = getEncryptedDataFromOriginal(BUDGET_KEY, amount);

            DateTimeFormatter inputDateTimeFormatter = CommonUtils.getDateTimeFormatter(session);

            Date from = CommonUtils.getDate(budgetCommand.getFrom(), inputDateTimeFormatter);
            Date to = CommonUtils.getDate(budgetCommand.getTo(), inputDateTimeFormatter);

            List<CategoryBudget> categoryBudgetList = new ArrayList<>();
            List<CategoryBudgetCommand> categoryBudgetCommandList = budgetCommand.getCategoryBudgets();

            if (categoryBudgetCommandList != null && categoryBudgetCommandList.size() > 0) {

                categoryBudgetList = categoryBudgetCommandList.stream()
                                                              .map(this::getCategoryBudgetFromCategoryBudgetCommand)
                                                              .filter(e -> e.isPresent())
                                                              .map(e -> e.get())
                                                              .collect(Collectors.toList());
            }

            budget.setName(nameEncrypted);
            budget.setAmount(amountEncrypted);
            budget.setFrom(from);
            budget.setTo(to);
            budget.setCategorybudgets(categoryBudgetList);

        } catch (CryptoException e) {
            log.info("Exception while encrypting budget fields.", e.getMessage());
        }

        return budget;
    }

    private Optional<CategoryBudget> getCategoryBudgetFromCategoryBudgetCommand(CategoryBudgetCommand categoryBudgetCommand) {

        CategoryBudget categoryBudget = new CategoryBudget();

        try {
            String categoryName = categoryBudgetCommand.getName();
            String categoryAmount = categoryBudgetCommand.getAmount();

            if (StringUtils.isBlank(categoryAmount) || StringUtils.isBlank(categoryAmount)) {

                return Optional.empty();
            }

            String categoryNameEncrypted = getEncryptedDataFromOriginal(BUDGET_KEY, categoryName);
            String categoryAmountEncrypted = getEncryptedDataFromOriginal(BUDGET_KEY, categoryAmount);

            categoryBudget.setType(categoryBudgetCommand.getType());
            categoryBudget.setName(categoryNameEncrypted);
            categoryBudget.setAmount(categoryAmountEncrypted);

        } catch (CryptoException e) {
            log.error("Exception while encrypting category budget information.", e.getMessage());
        }

        return Optional.of(categoryBudget);
    }
}
