package com.sb.services.calculation;

import com.sb.commands.TransactionCommand;
import com.sb.services.utils.CommonUtils;
import com.sb.services.utils.Constants;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kingsley Kumar on 25/01/2017.
 */
public class TransactionByNoBreakdown extends TransactionBreakdownAbstract {

    @Override
    public List<ResultNode> getResult(String userId,
                                      List<TransactionCommand> transactions,
                                      LocalDate from,
                                      LocalDate to,
                                      HttpSession session) {

        // Map Transactions to each date.

        Map<LocalDate, List<TransactionCommand>> mapDateWithTransactions = new HashMap<>();

        transactions.forEach(e -> mapTransactionToDate(mapDateWithTransactions, e, session));

        // Breakdown and return.

        List<ResultNode> resultNodes = new ArrayList<>();

        if(transactions == null || transactions.size() == 0) {

            return resultNodes;
        }

//        DateTimeFormatter userInputDateTimeFormatter = (DateTimeFormatter) session
//                .getAttribute("inputDateTimeFormatter");

//        Collections.sort(transactions, (o1, o2) -> {
//
//            LocalDate date1 = LocalDate.parse(o1.getDate(), userInputDateTimeFormatter);
//            LocalDate date2 = LocalDate.parse(o2.getDate(), userInputDateTimeFormatter);
//
//            return date1.compareTo(date2);
//        });

        ResultNode incomeNode = new ResultNode("Total Income");
        ResultNode expenseNode = new ResultNode("Total Spending");
        ResultNode savingsNode = new ResultNode("Savings");

        transactions.forEach(tx -> {

            if (tx.getType().equals(Constants.TX_TYPE_EXPENSE)) {

                expenseNode.addTransaction(tx);
            } else if (tx.getType().equals(Constants.TX_TYPE_INCOME)) {

                incomeNode.addTransaction(tx);
            }
        });

        updateResultNode(incomeNode);

        updateResultNode(expenseNode);

        if (incomeNode.getAmount() > expenseNode.getAmount()) {

            double totalSavings = incomeNode.getAmount() - expenseNode.getAmount();
            double totalSavingsInPercentage = (totalSavings * 100) / incomeNode.getAmount();

            savingsNode.setAmount(totalSavings);
            savingsNode.setAmountInPercentage(totalSavingsInPercentage);
        }

        String firstDayStr = from.format(Constants.DAY_MONTH_YEAR_FORMATATTER);
        String secondDayStr = to.format(Constants.DAY_MONTH_YEAR_FORMATATTER);

        String range = CommonUtils.concatenateStrings("[", firstDayStr, " - ", secondDayStr, "]");

        ResultNode resultNode = new ResultNode(range);

        resultNode.getChildren().add(incomeNode);
        resultNode.getChildren().add(expenseNode);
        resultNode.getChildren().add(savingsNode);

//            printNode(monthNode, 5);
//            printNode(expenseNode, 5);
//            printNode(savingsNode, 5);

        resultNodes.add(resultNode);

//        addAdditionalStatNodes(resultNodes, "Monthly");

        return resultNodes;
    }
}