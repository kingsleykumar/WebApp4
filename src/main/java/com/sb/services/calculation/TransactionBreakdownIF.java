package com.sb.services.calculation;

import com.sb.commands.BudgetCommand;
import com.sb.commands.TransactionCommand;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Kingsley Kumar on 25/09/2016.
 */
public interface TransactionBreakdownIF {

    List<ResultNode> getResult(String userId,
                               List<TransactionCommand> transactions,
                               LocalDate from,
                               LocalDate to,
                               HttpSession session);

    void setBudgetsList(List<BudgetCommand> budgetList);
}

