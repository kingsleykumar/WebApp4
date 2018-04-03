package com.sb.services.calculation;


import com.sb.commands.BudgetCommand;
import com.sb.commands.TransactionCommand;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kingsley Kumar on 25/09/2016.
 */
public class EmptyTransactionBreakdown implements TransactionBreakdownIF {

    @Override
    public List<ResultNode> getResult(String userId,
                                      List<TransactionCommand> transactions,
                                      LocalDate from,
                                      LocalDate to,
                                      HttpSession session) {

        return new ArrayList<>();
    }

    @Override
    public void setBudgetsList(List<BudgetCommand> budgetList) {
        //TBD
    }
}
