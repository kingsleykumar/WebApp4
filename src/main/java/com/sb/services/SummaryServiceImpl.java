package com.sb.services;

import com.sb.commands.BudgetCommand;
import com.sb.commands.TransactionCommand;
import com.sb.services.calculation.ResultNode;
import com.sb.services.calculation.TransactionBreakdownFactory;
import com.sb.services.calculation.TransactionBreakdownIF;
import com.sb.services.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kingsley Kumar on 30/03/2018 at 21:59.
 */
@Slf4j
@Service
public class SummaryServiceImpl implements SummaryService {

    private BudgetService budgetService;
    private TransactionService transactionService;

    public SummaryServiceImpl(BudgetService budgetService, TransactionService transactionService) {
        this.budgetService = budgetService;
        this.transactionService = transactionService;
    }

    @Override
    public List<ResultNode> retrieveSummary(HttpServletRequest req, HttpSession session) {

        String fromDateStr = req.getParameter("dateFrom");
        String toDateStr = req.getParameter("dateTo");

        if (StringUtils.isBlank(fromDateStr)) {

            log.error("From date is not selected.");

            return new ArrayList<>(0);
        }

        if (StringUtils.isBlank(toDateStr)) {

            log.error("To date is not selected.");

            return new ArrayList<>(0);
        }

        DateTimeFormatter userInputDateTimeFormatter = CommonUtils.getDateTimeFormatter(session);

        List<TransactionCommand> allTransactions = transactionService.retrieveTransactions(req, session);

        String selectedBreakDown = req.getParameter("selectedBreakDown");

        LocalDate fromDate = LocalDate.parse(fromDateStr, userInputDateTimeFormatter);
        LocalDate toDate = LocalDate.parse(toDateStr, userInputDateTimeFormatter);

        if (fromDate.isAfter(toDate)) {

            fromDate = toDate;
        }

        TransactionBreakdownIF transactionProcessor = TransactionBreakdownFactory.getTransactionBreakdown(selectedBreakDown);

        if(selectedBreakDown.equals("budget")) {

            List<BudgetCommand> allBudgets = budgetService.getAllBudgets(session);

            transactionProcessor.setBudgetsList(allBudgets);
        }

        String userId = String.valueOf(session.getAttribute("userid"));

        List<ResultNode> resultNodes =
                transactionProcessor.getResult(userId, allTransactions, fromDate, toDate, session);

        log.info("resultNodes size = " + (resultNodes == null ? 0 : resultNodes.size()));

        return resultNodes;
    }
}
