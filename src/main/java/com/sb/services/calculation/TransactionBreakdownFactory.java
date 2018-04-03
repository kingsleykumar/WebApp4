package com.sb.services.calculation;

import org.apache.logging.log4j.LogManager;

/**
 * Created by Kingsley Kumar on 25/09/2016.
 */
public class TransactionBreakdownFactory {

    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(TransactionBreakdownFactory.class);

    public static TransactionBreakdownIF getTransactionBreakdown(String breakDownType) {

        logger.info("breakDownType = " + breakDownType);

        switch (breakDownType) {

            case "nobreakdown":
                return new TransactionByNoBreakdown();

            case "monthly":
                return new TransactionBreakdownByMonthly();

            case "weekly":
                return new TransactionBreakdownByWeekly();

            case "daily":
                return new TransactionBreakdownByDaily();

            case "yearly":
                return new TransactionBreakdownByYearly();

            case "budget":
                return new TransactionBreakdownByBudget();

        }

        return new EmptyTransactionBreakdown();
    }

}
