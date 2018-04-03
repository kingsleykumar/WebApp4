package com.sb.services.calculation;

import com.sb.commands.TransactionCommand;
import com.sb.services.utils.CommonUtils;
import com.sb.services.utils.Constants;

import javax.servlet.http.HttpSession;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Kingsley Kumar on 02/10/2016.
 */
public class TransactionBreakdownByWeekly extends TransactionBreakdownAbstract {

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

        Map<String, List<TransactionCommand>> mapOfWeekWithTransactions = groupTransactionsByWeek(mapDateWithTransactions, from, to);

        // Breakdown and return.

        List<ResultNode> resultNodes = applyBreakDown(mapOfWeekWithTransactions);

        addAdditionalStatNodes(resultNodes, "Weekly");

        return resultNodes;
    }


    private Map<String, List<TransactionCommand>> groupTransactionsByWeek(Map<LocalDate, List<TransactionCommand>> mapDateWithTransactions,
                                                                   LocalDate from,
                                                                   LocalDate to) {

        List<LocalDate> dates = new ArrayList<>(mapDateWithTransactions.keySet());

        Collections.sort(dates, Comparator.reverseOrder());

//        logger.info("dates = " + dates);

        Map<String, List<TransactionCommand>> map = new LinkedHashMap<>();

        dates.forEach(e -> {

            LocalDate startDate = e.with(DayOfWeek.MONDAY);
            LocalDate endDate = e.with(DayOfWeek.SUNDAY);

            if (startDate.isBefore(from)) {
                startDate = from;
            }

            if (endDate.isAfter(to)) {
                endDate = to;
            }

            String firstDayStr = startDate.format(Constants.DAY_MONTH_YEAR_FORMATATTER);
            String secondDayStr = endDate.format(Constants.DAY_MONTH_YEAR_FORMATATTER);

            String weekStr = CommonUtils.concatenateStrings("[", firstDayStr, " - ", secondDayStr, "]");

//            logger.info("weekStr = " + weekStr);

            List<TransactionCommand> txList = map.get(weekStr);

            if (txList == null) {

                txList = new ArrayList<>();

                map.put(weekStr, txList);
            }

            txList.addAll(mapDateWithTransactions.get(e));
        });
//
//        for (String s : map.keySet()) {
//
//            logger.info("s = " + s);
//            logger.info("map.get(s).size() = " + map.get(s).size());
//        }

        return map;
    }
}
