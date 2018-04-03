package com.sb.services.calculation;


import com.sb.commands.TransactionCommand;
import com.sb.services.utils.Constants;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Kingsley Kumar on 25/09/2016.
 */
public class TransactionBreakdownByMonthly extends TransactionBreakdownAbstract {

    @Override
    public List<ResultNode> getResult(String userId,
                                      List<TransactionCommand> transactions,
                                      LocalDate from,
                                      LocalDate to,
                                      HttpSession session) {

        // Map Transactions to each date.

        Map<LocalDate, List<TransactionCommand>> mapDateWithTransactions = new HashMap<>();

        transactions.forEach(e -> mapTransactionToDate(mapDateWithTransactions, e, session));

        //Group transactions by Month.

        Map<String, List<TransactionCommand>> mapOfMonthWithTransactions = groupTransactionsByMonth(mapDateWithTransactions);

        // Breakdown and return.

        List<ResultNode> resultNodes = applyBreakDown(mapOfMonthWithTransactions);

        addAdditionalStatNodes(resultNodes, "Monthly");

        return resultNodes;
    }

    private Map<String, List<TransactionCommand>> groupTransactionsByMonth(Map<LocalDate, List<TransactionCommand>> mapDateWithTransactions) {

        List<LocalDate> dates = new ArrayList<>(mapDateWithTransactions.keySet());

        Collections.sort(dates, (d1, d2) -> d2.compareTo(d1));

        Map<String, List<TransactionCommand>> map = new LinkedHashMap<>();

        dates.forEach(e -> {

            String monthStr = e.format(Constants.MONTH_YEAR_FORMATTER);

            List<TransactionCommand> txList = map.get(monthStr);

            if (txList == null) {

                txList = new ArrayList<>();

                map.put(monthStr, txList);
            }

            txList.addAll(mapDateWithTransactions.get(e));
        });
//
//        for (String s : map.keySet()) {
//
//            System.out.println("s = " + s);
//            System.out.println("map.get(s).size() = " + map.get(s).size());
//        }

        return map;
    }

}
