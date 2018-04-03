package com.sb.services.utils;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kingsley Kumar on 01/09/2016.
 */
public class Constants {

    private static final String USER_INPUT_DATE_FORMAT = "dd/MM/yyyy";
    private static final String MONTH_YEAR_FORMAT = "MMM, yyyy";
    private static final String YEAR_FORMAT = "yyyy";
    private static final String DAY_MONTH_YEAR_FORMAT = "dd MMM yyyy";
    public static final String MONGO_DB_DATE_FORMAT = "dd-MM-yyyy";
    public static final String TX_TYPE_INCOME = "Income";
    public static final String TX_TYPE_EXPENSE = "Expense";
    private static final DecimalFormat SUMMARY_NUMBERS_FORMAT = new DecimalFormat("#,###.####");

    //    public static final DateTimeFormatter INPUT_DATE_TIME_FORMATTER = DateTimeFormatter
//            .ofPattern(Constants.USER_INPUT_DATE_FORMAT);
    public static final DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormatter
            .ofPattern(Constants.MONTH_YEAR_FORMAT);
    public static final DateTimeFormatter DAY_MONTH_YEAR_FORMATATTER = DateTimeFormatter
            .ofPattern(Constants.DAY_MONTH_YEAR_FORMAT);
    public static final DateTimeFormatter YEAR_FORMATATTER = DateTimeFormatter
            .ofPattern(Constants.YEAR_FORMAT);

//    public static DateTimeFormatter getInputDateTimeFormatter() {
//
//        return INPUT_DATE_TIME_FORMATTER;
//    }

    public static DecimalFormat getInputDecimalFormat() {

        return new DecimalFormat("#,###.####");
    }

    public static DecimalFormat getSummaryNumbersFormat() {

        return new DecimalFormat("#,###.####");
    }

//    public static ZonedDateTime getZonedDateTimeOnDate(String date, HttpSession session) {
//
//        DateTimeFormatter userInputDateTimeFormatter = (DateTimeFormatter) session.getAttribute("inputDateTimeFormatter");
//
//        return LocalDate.parse(date, userInputDateTimeFormatter)
//                        .atTime(0, 0, 0)
//                        .atZone(ZoneId.of("UTC"));
//    }

    public static List<String> getTransactionTypes() {

        List<String> types = new ArrayList<>();

        types.add(TX_TYPE_INCOME);
        types.add(TX_TYPE_EXPENSE);

        return types;
    }

    //SMTP Mail Settings.


    public static final String SALT = "";

    public static final String PW_RESET_STATUS_EMAIL_LINK_SENT = "";
    public static final String PW_RESET_STATUS_HASH_VERIFIED = "";
    public static final String PW_RESET_STATUS_PW_CHANGED = "";

    public static final String IS_RESET_HASH_VERIFIED = "";

    public static final String RESET_PASSWORD = "";
    public static final String ACTIVATION = "";

    public static final String TX_KEY = "";
    public static final String CATEGORY_KEY = "";
    public static final String BUDGET_KEY = "";
    public static final String SIGN_UP_KEY = "";
    public static final String MONGO_DB_NAME = "";
    public static final String MONGO_UN = "";
    public static final char[] MONGO_PW = "".toCharArray();

    public static final String BUDGET_ID = "budget";
    public static final String BUDGET_DISPLAY_NAME = "Budget";

    public static final String DEFAULT_PAGE_AFTER_LOGIN = "defaultPage";
    public static final String PAGE_CATEGORIES = "categories";
    public static final String PAGE_BUDGETS = "budgets";
    public static final String PAGE_ADD_TRANSACTION = "addTransaction";
    public static final String PAGE_VIEW_EDIT_TRANSACTION = "viewEditTransaction";
    public static final String PAGE_VIEW_SUMMARY = "viewSummary";

    public final static String DEFAULT_TIME_RANGE = "default";
    public final static String DAILY_TIME_RANGE = "daily";
    public final static String WEEKLY_TIME_RANGE = "weekly";
    public final static String MONTHLY_TIME_RANGE = "monthly";
    public final static String WTD_TIME_RANGE = "wtd";
    public final static String MONTH_TIME_RANGE = "mtd";

    // Supported date formats

    public static List<SelectionItem> getSupportedDateFormats() {

        List<SelectionItem> formats = new ArrayList<>();

        formats.add(new SelectionItem("dd/mm/yyyy", "dd/mm/yyyy (example: 01/05/2012)"));
        formats.add(new SelectionItem("dd.mm.yyyy", "dd.mm.yyyy (example: 01.05.2012)"));
        formats.add(new SelectionItem("dd-mm-yyyy", "dd-mm-yyyy (example: 01-05-2012)"));
        formats.add(new SelectionItem("yyyy-mm-dd", "yyyy-mm-dd (example: 2012-05-01)"));
        formats.add(new SelectionItem("yyyy.mm.dd", "yyyy.mm.dd (example: 2012.05.01)"));
        formats.add(new SelectionItem("mm/dd/yyyy", "mm/dd/yyyy (example: 05/01/2012)"));

        return formats;
    }

    public static List<SelectionItem> getSummaryBreakDownByList() {

        List<SelectionItem> list = new ArrayList<>();

        list.add(new SelectionItem("nobreakdown", ""));
        list.add(new SelectionItem("monthly", "Monthly"));
        list.add(new SelectionItem("weekly", "Weekly"));
        list.add(new SelectionItem("daily", "Daily"));
        list.add(new SelectionItem("yearly", "Yearly"));
        list.add(new SelectionItem(BUDGET_ID, BUDGET_DISPLAY_NAME));

        return list;
    }

    public static List<SelectionItem> getSummaryViewsList() {

        List<SelectionItem> list = new ArrayList<>();

        list.add(new SelectionItem("tableView", "Table Only"));
        list.add(new SelectionItem("chartView", "Chart Only"));
        list.add(new SelectionItem("bothViews", "Table & Chart"));

        return list;
    }

    public static List<SelectionItem> getSummaryChartTypesList() {

        List<SelectionItem> list = new ArrayList<>();

        list.add(new SelectionItem("bar", "Vertical Bar"));
        list.add(new SelectionItem("horizontalBar", "Horizontal Bar"));
        list.add(new SelectionItem("doughnut", "Pie"));

        return list;
    }

    public static List<SelectionItem> getSummaryTableExpandStateList() {

        List<SelectionItem> list = new ArrayList<>();

        list.add(new SelectionItem("expandToOneLevel", "Expand To One Level"));
        list.add(new SelectionItem("expandToTwoLevels", "Expand To Two Levels"));
        list.add(new SelectionItem("expandAll", "Expand All"));

        return list;
    }


    public static List<SelectionItem> getPagesList() {

        List<SelectionItem> list = new ArrayList<>();

        list.add(new SelectionItem(DEFAULT_PAGE_AFTER_LOGIN, ""));
        list.add(new SelectionItem(PAGE_CATEGORIES, "Categories"));
        list.add(new SelectionItem(PAGE_BUDGETS, "Budgets"));
        list.add(new SelectionItem(PAGE_ADD_TRANSACTION, "Add Transactions"));
        list.add(new SelectionItem(PAGE_VIEW_EDIT_TRANSACTION, "View/Edit Transactions"));
        list.add(new SelectionItem(PAGE_VIEW_SUMMARY, "View Summary"));

        return list;
    }

    public static List<SelectionItem> getTimeRangeOptions() {

        List<SelectionItem> list = new ArrayList<>();

        list.add(new SelectionItem(DEFAULT_TIME_RANGE, ""));
        list.add(new SelectionItem(DAILY_TIME_RANGE, "Daily"));
        list.add(new SelectionItem(WEEKLY_TIME_RANGE, "Weekly"));
        list.add(new SelectionItem(MONTHLY_TIME_RANGE, "Monthly"));
        list.add(new SelectionItem(WTD_TIME_RANGE, "Week To Date"));
        list.add(new SelectionItem(MONTH_TIME_RANGE, "Month To Date"));

        return list;
    }
}
