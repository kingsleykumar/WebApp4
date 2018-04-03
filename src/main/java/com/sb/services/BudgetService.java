package com.sb.services;

import com.sb.commands.BudgetCommand;
import com.sb.services.containers.ResultMessage;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * Created by Kingsley Kumar on 25/03/2018 at 22:37.
 */
public interface BudgetService {

    List<BudgetCommand> getAllBudgets(HttpSession session);

    Optional<ResultMessage> addBudget(BudgetCommand budgetCommand, HttpSession session);

    Optional<ResultMessage> updateBudget(BudgetCommand budgetCommand, HttpSession session);

    Optional<ResultMessage> deleteBudget(String budgetName, HttpSession session);

    Optional<BudgetCommand> retrieveSpecificBudget(String budgetName, HttpSession session);

}
