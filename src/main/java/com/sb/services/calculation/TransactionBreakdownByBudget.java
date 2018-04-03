package com.sb.services.calculation;

import com.sb.commands.BudgetCommand;
import com.sb.commands.TransactionCommand;
import com.sb.services.utils.CommonUtils;
import com.sb.services.utils.Constants;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Kingsley Kumar on 31/10/2016.
 */
@Slf4j
public class TransactionBreakdownByBudget extends TransactionBreakdownAbstract {

    @Override
    public List<ResultNode> getResult(String userId,
                                      List<TransactionCommand> transactions,
                                      LocalDate from,
                                      LocalDate to,
                                      HttpSession session) {

//        Date fromDate = Date.from(from.atStartOfDay(ZoneId.of("UTC")).toInstant());
//        Date toDate = Date.from(to.atStartOfDay(ZoneId.of("UTC")).toInstant());
//
//        List<Budget> budgetList = MongoManager.getInstance()
//                                              .getBudgetDAO()
//                                              .retrieveBudgets(userId, session);
//        List<Budget> budgetList = MongoManager.getInstance().getBudgetDAO(userId).retrieveBudgets(fromDate, toDate);

        log.info("budgetList.size() = " + budgetList.size());

        budgetList.forEach(e -> log.info(e.getName() + " , " + e.getFrom() + " , " + e.getTo()));

        //Group transactions by Budget.

        BudgetInfoContainer budgetInfoContainer = groupTransactionsByBudget(transactions, budgetList, session);

//        logger.info("mapOfMonthWithTransactions.keySet() = " + mapOfMonthWithTransactions.keySet());

//        mapOfMonthWithTransactions.keySet().forEach(e -> logger
//                .info("Budget = " + e + " , size = " + mapOfMonthWithTransactions.get(e).size()));

        // Breakdown and return.

        List<ResultNode> resultNodes = applyBreakDown(budgetInfoContainer, budgetList);

        return resultNodes;
    }

    private BudgetInfoContainer groupTransactionsByBudget(List<TransactionCommand> transactions,
                                                          List<BudgetCommand> budgetList,
                                                          HttpSession session) {

//        DateTimeFormatter userInputDateTimeFormatter = (DateTimeFormatter) session
//                .getAttribute("inputDateTimeFormatter");

        DateTimeFormatter userInputDateTimeFormatter = CommonUtils.getDateTimeFormatter(session);

        Collections.sort(transactions, (o1, o2) -> {

            LocalDate date1 = LocalDate.parse(o1.getDate(), userInputDateTimeFormatter);
            LocalDate date2 = LocalDate.parse(o2.getDate(), userInputDateTimeFormatter);

            return date2.compareTo(date1);
        });

        Map<String, List<TransactionCommand>> mapOfBudgetWithTxList = new LinkedHashMap<>();
        Map<String, String> mapBudgetNameWithFullName = new HashMap<>();

        transactions.forEach(tx -> {

            String budgetName = tx.getBudget();

            if (budgetName != null && !budgetName.isEmpty()) {

                String budgetNameUpdated = budgetName;

                if (mapBudgetNameWithFullName.containsKey(budgetName)) {

                    budgetNameUpdated = mapBudgetNameWithFullName.get(budgetName);
                } else {

                    Optional<BudgetCommand> budgetOptional = budgetList.stream()
                                                                       .filter(e -> e.getName()
                                                                                     .equals(budgetName))
                                                                       .findFirst();

                    if (budgetOptional.isPresent()) {

                        LocalDate fromDate = LocalDate
                                .parse(budgetOptional.get()
                                                     .getFrom(), userInputDateTimeFormatter);
                        LocalDate toDate = LocalDate
                                .parse(budgetOptional.get()
                                                     .getTo(), userInputDateTimeFormatter);

                        String fromDateStr = fromDate.format(Constants.DAY_MONTH_YEAR_FORMATATTER);
                        String toDateStr = toDate.format(Constants.DAY_MONTH_YEAR_FORMATATTER);

                        budgetNameUpdated = CommonUtils
                                .concatenateStrings(budgetName, " [", fromDateStr, " - ", toDateStr, "]");

                        mapBudgetNameWithFullName.put(budgetName, budgetNameUpdated);
                    }
                }

                List<TransactionCommand> txList = mapOfBudgetWithTxList.get(budgetName);

                if (txList == null) {

                    txList = new ArrayList<>();

                    mapOfBudgetWithTxList.put(budgetName, txList);
                }

                txList.add(tx);
            }

        });

        return new BudgetInfoContainer(mapOfBudgetWithTxList, mapBudgetNameWithFullName);
    }

    protected List<ResultNode> applyBreakDown(BudgetInfoContainer budgetInfoContainer, List<BudgetCommand> budgetList) {

        List<ResultNode> resultNodes = new ArrayList<>();

        Map<String, List<TransactionCommand>> mapOfBudgetWithTxList = budgetInfoContainer.getMapOfBudgetWithTxList();
        Map<String, String> mapBudgetNameWithFullName = budgetInfoContainer.getMapBudgetNameWithFullName();

        mapOfBudgetWithTxList.keySet()
                             .forEach(budgetName -> {

                                 ResultNodeBudget expenseNode = new ResultNodeBudget("Total Spending");

                                 List<TransactionCommand> allTx = mapOfBudgetWithTxList.get(budgetName);

                                 Set<String> categoriesForWhichTxExists = new HashSet<>();
                                 List<String> categoriesForWhichTxDoesNotExist = new ArrayList<>();

                                 allTx.forEach(tx -> {

                                     if (tx.getType()
                                           .equals(Constants.TX_TYPE_EXPENSE)) {

                                         expenseNode.addTransaction(tx);

                                         categoriesForWhichTxExists.add(tx.getCategory());
                                     }
                                 });

                                 Optional<BudgetCommand> budgetOptional = budgetList.stream()
                                                                             .filter(budget -> budget.getName()
                                                                                                     .equals(budgetName))
                                                                             .findFirst();

                                 updateResultNode(expenseNode);

                                 // Find categories for which tx doesn't exist
                                 if (budgetOptional.isPresent()) {

                                     BudgetCommand budget = budgetOptional.get();

                                     budget.getCategoryBudgets()
                                           .stream()
                                           .filter(e -> !categoriesForWhichTxExists.contains(e.getName()))
//                                           .sorted((o1, o2) -> sortBudgetCategoryByAllocatedAmount(o1, o2))
                                           .map(e -> e.getName())
                                           .forEach(categoriesForWhichTxDoesNotExist::add);

                                     // Add an entry under expense node for the categories for which tx doesn't exist
                                     categoriesForWhichTxDoesNotExist.forEach(e -> expenseNode.getChildren()
                                                                                              .add(new ResultNodeBudget(
                                                                                                      e)));
                                 }

                                 String budgetFullName = mapBudgetNameWithFullName.get(budgetName);

                                 ResultNodeBudget resultNode = new ResultNodeBudget(budgetFullName);

                                 resultNode.getChildren()
                                           .add(expenseNode);

                                 resultNodes.add(resultNode);

                                 // Update Budget info on Total Expense Node

                                 log.info("budgetName === " + budgetName);
                                 log.info("budgetOptional.isPresent() == " + budgetOptional.isPresent());

                                 if (budgetOptional.isPresent()) {

                                     log.info("budgetOptional.get().getAmount() == " + budgetOptional.get()
                                                                                                     .getAmount());
                                     try {
                                         double budgetAmt = Double.valueOf(budgetOptional.get()
                                                                                         .getAmount());
                                         double actualSpentAmt = expenseNode.getAmount();
                                         double remainingAmt = 0;
                                         if (budgetAmt > 0)
                                             remainingAmt = budgetAmt - actualSpentAmt;
                                         expenseNode.setAllocatedAmount(budgetAmt);
                                         expenseNode.setRemainingAmount(remainingAmt);
                                     } catch (NumberFormatException ex) {
                                         log.error("allocatedAmt == " + budgetOptional.get()
                                                                                      .getAmount());
                                         log.error("NumberFormatException : " + ex.getMessage());
//                    e1.printStackTrace();
                                     }
                                 }

                                 // Update budget info on each Categories

                                 expenseNode.getChildren()
                                            .forEach(node -> {

                                                if (budgetOptional.isPresent()) {

                                                    BudgetCommand budget = budgetOptional.get();

                                                    double categoryAllocatedAmount = budget.getCategoryBudgets()
                                                                                           .stream()
                                                                                           .filter(e -> e.getName()
                                                                                                         .equals(node.getName()))
                                                                                           .map(e -> Double.valueOf(e.getAmount()))
                                                                                           .findFirst()
                                                                                           .orElse(0d);

                                                    log.info("node.getClass() = " + node.getClass());
                                                    log.info("categoryAllocatedAmount = " + categoryAllocatedAmount);

                                                    try {
//                        double budgetAmt = Double.valueOf(budgetOptional.get().getAmount());
                                                        double actualSpentAmt = node.getAmount();
                                                        double remainingAmt = 0;
                                                        if (categoryAllocatedAmount > 0)
                                                            remainingAmt = categoryAllocatedAmount - actualSpentAmt;

                                                        ((ResultNodeBudget) node).setAllocatedAmount(
                                                                categoryAllocatedAmount);
                                                        ((ResultNodeBudget) node).setRemainingAmount(remainingAmt);
                                                    } catch (NumberFormatException ex) {
                                                        log.error("allocatedAmt == " + budgetOptional.get()
                                                                                                        .getAmount());
                                                        log.error("NumberFormatException : " + ex.getMessage());
//                    e1.printStackTrace();
                                                    }
                                                }
                                            });
                             });


        return resultNodes;
    }

//    private int sortBudgetCategoryByAllocatedAmount(Budget.CategoryBudget o1, Budget.CategoryBudget o2) {
//
//        if (!StringUtils.isBlank(o1.getAmount()) && !StringUtils.isBlank(o2.getAmount())) {
//
//            int result = 0;
//            try {
//                Integer amountFirst = Integer.parseInt(o1.getAmount());
//                Integer amountSecond = Integer.parseInt(o2.getAmount());
//                result = amountSecond.compareTo(amountFirst);
//
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//                log.error("Exception while sorting category amount", e);
//            }
//
//            return result;
//        }
//
//        return 0;
//    }

    private static class BudgetInfoContainer {

        private Map<String, List<TransactionCommand>> mapOfBudgetWithTxList;
        private Map<String, String> mapBudgetNameWithFullName;

        public BudgetInfoContainer(Map<String, List<TransactionCommand>> mapOfBudgetWithTxList,
                                   Map<String, String> mapBudgetNameWithFullName) {
            this.mapOfBudgetWithTxList = mapOfBudgetWithTxList;
            this.mapBudgetNameWithFullName = mapBudgetNameWithFullName;
        }

        public Map<String, List<TransactionCommand>> getMapOfBudgetWithTxList() {
            return mapOfBudgetWithTxList;
        }

        public Map<String, String> getMapBudgetNameWithFullName() {
            return mapBudgetNameWithFullName;
        }
    }
}
