package com.sb.services.calculation;


import com.sb.commands.TransactionCommand;
import com.sb.services.utils.Constants;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Kingsley Kumar on 02/10/2016.
 */
public class TransactionBreakdownByDaily extends TransactionBreakdownAbstract {

    @Override
    public List<ResultNode> getResult(String userId,
                                      List<TransactionCommand> transactions,
                                      LocalDate from,
                                      LocalDate to,
                                      HttpSession session) {

        // Map Transactions to each date.

        Map<LocalDate, List<TransactionCommand>> mapDateWithTransactions = new HashMap<>();

        transactions.forEach(e -> mapTransactionToDate(mapDateWithTransactions, e, session));

        //Group transactions by Weekly.

        Map<String, List<TransactionCommand>> mapOfTransactions = groupTransactionsByDaily(mapDateWithTransactions);

        // Breakdown and return.

        List<ResultNode> resultNodes = applyBreakDown(mapOfTransactions);

        addAdditionalStatNodes(resultNodes, "Daily");

        return resultNodes;
    }

    private Map<String, List<TransactionCommand>> groupTransactionsByDaily(Map<LocalDate, List<TransactionCommand>> mapDateWithTransactions) {

        List<LocalDate> dates = new ArrayList<>(mapDateWithTransactions.keySet());

        Collections.sort(dates, Comparator.reverseOrder());

//        System.out.println("dates = " + dates);

        Map<String, List<TransactionCommand>> map = new LinkedHashMap<>();

        dates.forEach(e -> {

            String dayStr = e.format(Constants.DAY_MONTH_YEAR_FORMATATTER);

            List<TransactionCommand> txList = map.get(dayStr);

            if (txList == null) {

                txList = new ArrayList<>();

                map.put(dayStr, txList);
            }

            txList.addAll(mapDateWithTransactions.get(e));
        });

        return map;
    }
}
