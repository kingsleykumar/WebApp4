package com.sb.services;

import com.sb.commands.BudgetCommand;
import com.sb.convertors.BudgetCommandToBudget;
import com.sb.convertors.BudgetToBudgetCommand;
import com.sb.domain.Budget;
import com.sb.repositories.BudgetRepository;
import com.sb.repositories.TransactionRepository;
import com.sb.services.containers.ResultMessage;
import com.sb.services.utils.Constants;
import com.sb.services.utils.CryptoException;
import com.sb.services.utils.CryptoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Kingsley Kumar on 25/03/2018 at 22:38.
 */
@Slf4j
@Service
public class BudgetServiceImpl implements BudgetService {

    private BudgetRepository budgetRepository;
    private BudgetToBudgetCommand budgetToBudgetCommand;
    private BudgetCommandToBudget budgetCommandToBudget;
    private TransactionRepository transactionRepository;

    public BudgetServiceImpl(BudgetRepository budgetRepository,
                             BudgetToBudgetCommand budgetToBudgetCommand,
                             BudgetCommandToBudget budgetCommandToBudget,
                             TransactionRepository transactionRepository) {

        this.budgetRepository = budgetRepository;
        this.budgetToBudgetCommand = budgetToBudgetCommand;
        this.budgetCommandToBudget = budgetCommandToBudget;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<BudgetCommand> getAllBudgets(HttpSession session) {

        Object userId = session.getAttribute("userid");

        Optional<List<Budget>> budgetsOptional = budgetRepository.findAllByUserId(String.valueOf(userId));

        List<Budget> budgetList = budgetsOptional.orElse(new ArrayList<>(0));

        return budgetList.stream()
                         .sorted((o1, o2) -> o2.getFrom().compareTo(o1.getFrom()))
                         .map(budget -> budgetToBudgetCommand.convert(budget))
                         .collect(Collectors.toList());
    }

    @Override
    public Optional<ResultMessage> addBudget(BudgetCommand budgetCommand, HttpSession session) {

        Object userId = session.getAttribute("userid");

        Budget budget = budgetCommandToBudget.convert(budgetCommand);

        String budgetName = budget.getName();

        Optional<Budget> budgetFromDb = budgetRepository.findByUserIdAndName(String.valueOf(userId),
                                                                             budgetName);
        if (budgetFromDb.isPresent()) {

            log.info("Budget already exists. Please enter different name.");

            return Optional.of(new ResultMessage(false, "Budget already exists. Please enter different name."));
        }

        budget.setUserId(String.valueOf(userId));

        Budget budgetSaved = budgetRepository.save(budget);

        log.info("budgetSaved id = " + budgetSaved.get_id());

        return Optional.empty();
    }

    @Override
    public Optional<ResultMessage> updateBudget(BudgetCommand budgetCommand, HttpSession session) {

        Object userId = session.getAttribute("userid");

        Budget budget = budgetCommandToBudget.convert(budgetCommand);

        String budgetName = budget.getName();

        Optional<Budget> budgetFromDb = budgetRepository.findByUserIdAndName(String.valueOf(userId),
                                                                             budgetName);
        if (budgetFromDb.isPresent()) {

            budget.set_id(budgetFromDb.get().get_id());
        }

        budget.setUserId(String.valueOf(userId));

        Budget budgetSaved = budgetRepository.save(budget);

        log.info("budgetSaved id = " + budgetSaved.get_id());

        return Optional.empty();
    }

    @Override
    public Optional<ResultMessage> deleteBudget(String budgetName, HttpSession session) {

        Object userId = session.getAttribute("userid");

        String budgetNameEncrypted = budgetName;
        try {
            budgetNameEncrypted = CryptoUtils.getEncryptedDataFromOriginal(Constants.TX_KEY, budgetName);
        } catch (CryptoException e) {
            log.error("Exception while encrypting budget name.", e.getMessage());
        }

        boolean anyTxExistsForTheBudget = transactionRepository.anyTransactionExistsForBudget(String.valueOf(userId),
                                                                                              budgetNameEncrypted);

        if (anyTxExistsForTheBudget) {

            return Optional.of(new ResultMessage(false,
                                                 "Budget can't be deleted. At-least one transaction exists with this budget."));
        }

        try {
            budgetRepository.deleteByUserIdAndName(String.valueOf(userId),
                                                   CryptoUtils.getEncryptedDataFromOriginal(Constants.BUDGET_KEY,
                                                                                            budgetName));
        } catch (CryptoException e) {
            log.error("Exception while encrypting budget name.", e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Optional<BudgetCommand> retrieveSpecificBudget(String budgetName, HttpSession session) {

        Object userId = session.getAttribute("userid");

        String budgetNameEncrypted = budgetName;
        try {
            budgetNameEncrypted = CryptoUtils.getEncryptedDataFromOriginal(Constants.BUDGET_KEY, budgetName);
        } catch (CryptoException e) {
            log.error("Exception while encrypting budget name.", e.getMessage());
        }

        Optional<Budget> budgetFromDb = budgetRepository.findByUserIdAndName(String.valueOf(userId),
                                                                             budgetNameEncrypted);

        if (budgetFromDb.isPresent()) {

            BudgetCommand budgetCommand = budgetToBudgetCommand.convert(budgetFromDb.get());

            return Optional.of(budgetCommand);
        }

        return Optional.empty();
    }
}
