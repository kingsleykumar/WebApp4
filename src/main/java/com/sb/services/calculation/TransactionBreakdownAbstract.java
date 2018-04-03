package com.sb.services.calculation;


import com.sb.commands.BudgetCommand;
import com.sb.commands.TransactionCommand;
import com.sb.services.utils.CommonUtils;
import com.sb.services.utils.Constants;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Created by Kingsley Kumar on 02/10/2016.
 */
@Slf4j
public abstract class TransactionBreakdownAbstract implements TransactionBreakdownIF {

    protected List<BudgetCommand> budgetList = new ArrayList<>(0);

    protected void mapTransactionToDate(Map<LocalDate, List<TransactionCommand>> mapDateWithTransactions,
                                        TransactionCommand e,
                                        HttpSession session) {

//        DateTimeFormatter userInputDateTimeFormatter = (DateTimeFormatter) session
//                .getAttribute("inputDateTimeFormatter");
        DateTimeFormatter userInputDateTimeFormatter = CommonUtils.getDateTimeFormatter(session);


//        LocalDate date = CommonUtils.asLocalDate(e.getDate());
        LocalDate date = LocalDate.parse(e.getDate(), userInputDateTimeFormatter);

        List<TransactionCommand> txList = mapDateWithTransactions.get(date);

        if (txList == null) {

            txList = new ArrayList<>();

            mapDateWithTransactions.put(date, txList);
        }

        txList.add(e);

        mapDateWithTransactions.put(LocalDate.parse(e.getDate(), userInputDateTimeFormatter), txList);
    }

    protected List<ResultNode> applyBreakDown(Map<String, List<TransactionCommand>> mapOfMonthWithTransactions) {

        List<ResultNode> resultNodes = new ArrayList<>();

        mapOfMonthWithTransactions.keySet().forEach(e -> {

            ResultNode incomeNode = new ResultNode("Total Income");
            ResultNode expenseNode = new ResultNode("Total Spending");
            ResultNode savingsNode = new ResultNode("Savings");

            List<TransactionCommand> allTx = mapOfMonthWithTransactions.get(e);

            allTx.forEach(tx -> {

                if (tx.getType().equals(Constants.TX_TYPE_EXPENSE)) {

                    expenseNode.addTransaction(tx);
                } else if (tx.getType().equals(Constants.TX_TYPE_INCOME)) {

                    incomeNode.addTransaction(tx);
                }
            });

            updateResultNode(incomeNode);

            updateResultNode(expenseNode);

            if (incomeNode.getAmount() > 0 && incomeNode.getAmount() > expenseNode.getAmount()) {

                double totalSavings = incomeNode.getAmount() - expenseNode.getAmount();
                double totalSavingsInPercentage = (totalSavings * 100) / incomeNode.getAmount();

                savingsNode.setAmount(totalSavings);
                savingsNode.setAmountInPercentage(totalSavingsInPercentage);
            }

            ResultNode resultNode = new ResultNode(e);

            resultNode.getChildren().add(incomeNode);
            resultNode.getChildren().add(expenseNode);
            resultNode.getChildren().add(savingsNode);

//            printNode(monthNode, 5);
//            printNode(expenseNode, 5);
//            printNode(savingsNode, 5);

            resultNodes.add(resultNode);
        });

        return resultNodes;
    }

    protected void updateResultNode(ResultNode incomeNode) {

        incomeNode.updateUndefinedCategories();
        incomeNode.updatePercentage();
        incomeNode.sortNodesByHighestPercentage();
    }

    protected void addAdditionalStatNodes(List<ResultNode> resultNodes, String breakDownPeriod) {

        if (resultNodes.size() > 1) {

            Double totalSpending = resultNodes.stream()
                                              .map(resultNode -> resultNode.getChildren().get(1).getAmount())
                                              .reduce((amount1, amount2) -> (amount1 + amount2))
                                              .orElse(0d);

            double avgSpending = totalSpending / resultNodes.size();

            resultNodes.add(new ResultNode("Total spending in this period").setAmount(totalSpending));
            resultNodes.add(new ResultNode("Average " + breakDownPeriod.toLowerCase() + " spending")
                    .setAmount(avgSpending));
        }
    }


    @Override
    public void setBudgetsList(List<BudgetCommand> budgetList) {

        if(budgetList != null && budgetList.size() > 0) {

            this.budgetList = budgetList;
        }
    }

    protected void printNode(ResultNode expenseNode, int noOfSpace) {

        System.out.print(expenseNode.getName() + " :: " + expenseNode.getAmount() + " :: " + expenseNode
                .getAmountInPercentage());

        System.out.println();

        expenseNode.getChildren().forEach(e -> {

            IntStream.range(0, noOfSpace)
                     .forEach(i -> System.out.print(" "));

            System.out.print("|___");

            if (e.getChildren().size() > 0) {

                printNode(e, (noOfSpace + 5));
            } else {

                System.out.print(e.getName() + " :: " + e.getAmount() + " :: " + e
                        .getAmountInPercentage());

                System.out.println();
            }

//            System.out.print(e.getName() + " :: " + e.getAmount() + " :: " + e
//                    .getAmountInPercentage());
//
//            System.out.println();
        });
    }
}
