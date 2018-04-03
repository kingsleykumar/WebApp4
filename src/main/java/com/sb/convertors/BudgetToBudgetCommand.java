package com.sb.convertors;

import com.sb.commands.BudgetCommand;
import com.sb.commands.CategoryBudgetCommand;
import com.sb.domain.Budget;
import com.sb.domain.CategoryBudget;
import com.sb.services.utils.CommonUtils;
import com.sb.services.utils.CryptoException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sb.services.utils.Constants.BUDGET_KEY;
import static com.sb.services.utils.CryptoUtils.getOriginalDataFromEncrypted;

/**
 * Created by Kingsley Kumar on 25/03/2018 at 22:41.
 */
@Slf4j
@Setter
@Component
public class BudgetToBudgetCommand implements Converter<Budget, BudgetCommand> {

    @Override
    public BudgetCommand convert(Budget budget) {

        ServletRequestAttributes attr = (ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes();
        HttpSession session= attr.getRequest().getSession(false);

        BudgetCommand budgetCommand = new BudgetCommand();

        try {
            String name = budget.getName();
            String nameOriginal = getOriginalDataFromEncrypted(BUDGET_KEY, name);

            String amount = budget.getAmount();
            String amountOriginal = getOriginalDataFromEncrypted(BUDGET_KEY, amount);

            DateTimeFormatter inputDateTimeFormatter = CommonUtils.getDateTimeFormatter(session);

            String from = CommonUtils.getDateStr(budget.getFrom(), inputDateTimeFormatter);
            String to = CommonUtils.getDateStr(budget.getTo(), inputDateTimeFormatter);

            List<CategoryBudgetCommand> categoryBudgetCommandList = new ArrayList<>();
            List<CategoryBudget> categoryBudgetList = budget.getCategorybudgets();

            if (categoryBudgetList != null && categoryBudgetList.size() > 0) {

                categoryBudgetCommandList = categoryBudgetList.stream()
                                                              .map(this::getCategoryBudgetCommandFromCategoryBudget)
                                                              .collect(Collectors.toList());
            }

            budgetCommand.setName(nameOriginal);
            budgetCommand.setAmount(amountOriginal);
            budgetCommand.setFrom(from);
            budgetCommand.setTo(to);
            budgetCommand.setCategoryBudgets(categoryBudgetCommandList);

        } catch (CryptoException e) {
            log.info("Exception while decrypting budget fields.", e.getMessage());
        }


        return budgetCommand;
    }


    private CategoryBudgetCommand getCategoryBudgetCommandFromCategoryBudget(CategoryBudget categoryBudget) {

        CategoryBudgetCommand categoryBudgetCommand = new CategoryBudgetCommand();

        try {
            String categoryName = categoryBudget.getName();
            String categoryNameOriginal = getOriginalDataFromEncrypted(BUDGET_KEY, categoryName);

            String categoryAmount = categoryBudget.getAmount();
            String categoryAmountOriginal = getOriginalDataFromEncrypted(BUDGET_KEY, categoryAmount);

            categoryBudgetCommand.setType(categoryBudget.getType());
            categoryBudgetCommand.setName(categoryNameOriginal);
            categoryBudgetCommand.setAmount(categoryAmountOriginal);

        } catch (CryptoException e) {
            log.error("Exception while decrypting category budget information.", e.getMessage());
        }

        return categoryBudgetCommand;
    }
}
