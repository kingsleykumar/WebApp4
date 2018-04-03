package com.sb.controllers;

import com.google.gson.Gson;
import com.sb.commands.BudgetCommand;
import com.sb.commands.TransactionCommand;
import com.sb.commands.TransactionCommandWrapper;
import com.sb.services.BudgetService;
import com.sb.services.CategoryService;
import com.sb.services.TransactionService;
import com.sb.services.containers.ResultMessage;
import com.sb.services.utils.CommonUtils;
import com.sb.services.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.sb.services.utils.Constants.WEEKLY_TIME_RANGE;

/**
 * Created by Kingsley Kumar on 28/03/2018 at 00:21.
 */
@Slf4j
@Controller
public class TransactionController extends ControllerAbstract {

    private TransactionService transactionService;
    private CategoryService categoryService;
    private BudgetService budgetService;

    public TransactionController(TransactionService transactionService,
                                 CategoryService categoryService,
                                 BudgetService budgetService) {
        this.transactionService = transactionService;
        this.categoryService = categoryService;
        this.budgetService = budgetService;
    }

    @GetMapping("/transaction/add")
    public String getTransactionAddPage(Model model, HttpSession session) {

        log.info("---- TransactionController.getTransactionAddPage ----");

        List<String[]> categoryList = categoryService.getCategories(session);

        Map<String, List<String>> categorySubCategoriesMap = new HashMap<>();

        List<String> categories = getCategories(categoryList, false);

        categoryList.stream().forEach(row -> categorySubCategoriesMap.put(row[0], getSubCategories(row)));

        List<BudgetCommand> budgetCommands = budgetService.getAllBudgets(session);

        List<String> budgets = budgetCommands.stream()
                                             .sorted(getBudgetCommandComparator(session))
                                             .map(budgetCommand -> budgetCommand.getName())
                                             .collect(Collectors.toList());

        budgets.add(0, "");

        String dateFormat = String.valueOf(session.getAttribute("dateFormatJQuery"));

        Map<String, String> dateBudgetMap = new HashMap<>();

        updateDateBudgetMap(session, budgetCommands, dateBudgetMap);

        TransactionCommand transactionCommand = new TransactionCommand();

        // Default transaction command
        transactionCommand.setType(Constants.TX_TYPE_EXPENSE);
//        transactionCommand.setCategory("Food");
//        transactionCommand.setSubcategory("Lunch");
//        transactionCommand.setItem("Lunch & Dinner");
//        transactionCommand.setValue("3.40");
        transactionCommand.setId("0");
//        transactionCommand.setDate("25/03/2018");

        Gson gson = new Gson();

        model.addAttribute("transactionTypes", Constants.getTransactionTypes());
        model.addAttribute("categories", categories);
        model.addAttribute("subcategoriesJson", gson.toJson(categorySubCategoriesMap));
        model.addAttribute("dateBudgetMapJson", gson.toJson(dateBudgetMap));
        model.addAttribute("budgets", budgets);
        model.addAttribute("dateformat", dateFormat);
        model.addAttribute("title", "Add Transaction");

        List<TransactionCommand> transactionCommands = new ArrayList<>();

        transactionCommands.add(transactionCommand);

        TransactionCommandWrapper transactionCommandWrapper = new TransactionCommandWrapper(transactionCommands);

        model.addAttribute("transactionCommandWrapper", transactionCommandWrapper);

        return "view/addtransaction";
    }

    @PostMapping("/transaction/add")
    public String handlePostFromTransactionAddPage(@ModelAttribute TransactionCommandWrapper transactionCommandWrapper,
                                                   Model model,
                                                   HttpSession session) {

        log.info("---- TransactionController.handlePostFromTransactionAddPage ----");

//        log.info("transactionCommandWrapper = " + transactionCommandWrapper);

        Optional<ResultMessage> resultMessage = transactionService.addTransactions(transactionCommandWrapper, session);

        if (resultMessage.isPresent()) {

            log.info("resultMessage.get() = " + resultMessage.get());
        }

        return "redirect:/transaction/add";
    }


    @GetMapping("/transaction/view")
    public String getTransactionViewPage(Model model, HttpSession session) {

        log.info("---- TransactionController.getTransactionViewPage ----");

        List<String[]> categoryList = categoryService.getCategories(session);

        Map<String, List<String>> categorySubCategoriesMap = new HashMap<>();

        List<String> categories = getCategories(categoryList, false);

        categoryList.stream().forEach(row -> categorySubCategoriesMap.put(row[0], getSubCategories(row)));

        List<BudgetCommand> budgetCommands = budgetService.getAllBudgets(session);

        List<String> budgets = budgetCommands.stream()
                                             .sorted(getBudgetCommandComparator(session))
                                             .map(budgetCommand -> budgetCommand.getName())
                                             .collect(Collectors.toList());

        budgets.add(0, "");

        String dateFormat = String.valueOf(session.getAttribute("dateFormatJQuery"));
        String defaultTimeRangeForTxView = String.valueOf(session.getAttribute("defaultTimeRangeForTxView"));

        Gson gson = new Gson();

        model.addAttribute("transactionTypes", Constants.getTransactionTypes());
        model.addAttribute("categories", categories);
        model.addAttribute("subcategoriesJson", gson.toJson(categorySubCategoriesMap));
        model.addAttribute("budgets", budgets);
        model.addAttribute("dateformat", dateFormat);
        model.addAttribute("title", "Transactions");
        model.addAttribute("defaultTimeRange", defaultTimeRangeForTxView);

        List<TransactionCommand> transactionCommands = new ArrayList<>();

//        transactionCommands.add(transactionCommand);

        TransactionCommandWrapper transactionCommandWrapper = new TransactionCommandWrapper(transactionCommands);

        model.addAttribute("transactionCommandWrapper", transactionCommandWrapper);

        return "view/viewtransaction";
    }

    private String[] calculateDefaultTimeRange(HttpSession session) {

        String defaultTimeRangeForTxView = String.valueOf(session.getAttribute("defaultTimeRangeForTxView"));
        String[] timeRange = new String[0];

        DateTimeFormatter dateTimeFormatter = CommonUtils.getDateTimeFormatter(session);

        switch (defaultTimeRangeForTxView) {

            case WEEKLY_TIME_RANGE:



//                url = "redirect:/budget/list";
//                break;
//
//            case MONTHLY_TIME_RANGE:
//                url = "redirect:/transaction/add";
//                break;
//
//            case WTD_TIME_RANGE:
//                url = "redirect:/transaction/view";
//                break;
//
//            case MONTH_TIME_RANGE:
//                url = "redirect:/summary/view";
//                break;

            default:
                timeRange = new String[]{"",""};
        }

        return timeRange;
    }

    @GetMapping("/transaction/retrieve")
    public void retrieveTransactions(HttpServletRequest req,
                                     HttpServletResponse resp,
                                     HttpSession session) throws IOException {

        log.info("---- TransactionController.retrieveTransactions ----");

//        printParameterMap(req);

//        List<TransactionCommand> transactionCommands = new ArrayList<>();
        List<TransactionCommand> transactionCommands = transactionService.retrieveTransactions(req, session);

//        TransactionCommand transactionCommand = new TransactionCommand();
//
//        // Default transaction command
//        transactionCommand.setType(Constants.TX_TYPE_EXPENSE);
//        transactionCommand.setCategory("Food");
//        transactionCommand.setSubcategory("Lunch");
//        transactionCommand.setItem("Lunch & Dinner");
//        transactionCommand.setValue("3.40");
//        transactionCommand.setId("0");
//        transactionCommand.setDate("28/03/2018");
//        transactionCommand.setBudget("March 2018");
//        transactionCommand.setDelete(true);

//        transactionCommands.add(transactionCommand);

        Gson gson = new Gson();

        log.info("gson.toJson(transactions) = " + gson.toJson(transactionCommands));

        resp.getWriter()
            .print(gson.toJson(transactionCommands));
    }


    @PostMapping("/transaction/update")
    public String handlePostFromTransactionUpdatePage(@ModelAttribute TransactionCommandWrapper transactionCommandWrapper,
                                                      Model model,
                                                      HttpSession session) {

        log.info("---- TransactionController.handlePostFromTransactionUpdatePage ----");

        log.info("transactionCommandWrapper = " + transactionCommandWrapper);

        Optional<ResultMessage> resultMessage = transactionService.updateTransactions(transactionCommandWrapper,
                                                                                      session);

        if (resultMessage.isPresent()) {

            log.info("resultMessage.get() = " + resultMessage.get());
        }

        return "redirect:/transaction/view";
    }


    private List<String> getSubCategories(String[] categoryRow) {

        List<String> subCategories = new ArrayList<>();

        String subCategoriesString = categoryRow[1];

        if (!StringUtils.isBlank(subCategoriesString)) {

            subCategories = Arrays.stream(subCategoriesString.split(","))
                                  .map(e -> e.trim())
                                  .collect(Collectors.toList());
        }

        subCategories.add(0, "");

        return subCategories;
    }

    private void updateDateBudgetMap(HttpSession session,
                                     List<BudgetCommand> budgetCommands,
                                     Map<String, String> dateBudgetMap) {

        DateTimeFormatter userInputDateTimeFormatter = CommonUtils.getDateTimeFormatter(session);

        LocalDate currentDate = LocalDate.now();

        IntStream.range(0, 2)
                 .forEach(e -> {

                     updateDateBudgetMapOnGivenDate(budgetCommands,
                                                    dateBudgetMap,
                                                    userInputDateTimeFormatter,
                                                    currentDate.minusDays(e));

                     updateDateBudgetMapOnGivenDate(budgetCommands,
                                                    dateBudgetMap,
                                                    userInputDateTimeFormatter,
                                                    currentDate.plusDays(e));
                 });
    }

    private void updateDateBudgetMapOnGivenDate(List<BudgetCommand> budgetCommands,
                                                Map<String, String> mapDateWithBudget,
                                                DateTimeFormatter userInputDateTimeFormatter, LocalDate newDate) {

        String newDateStr = userInputDateTimeFormatter.format(newDate);

        String budgetName = getBudgetNameOnDate(budgetCommands, userInputDateTimeFormatter, newDate);

        if (!budgetName.isEmpty())
            mapDateWithBudget.put(newDateStr, budgetName);
    }

    private String getBudgetNameOnDate(List<BudgetCommand> budgetList,
                                       DateTimeFormatter userInputDateTimeFormatter,
                                       LocalDate newDate) {

        String budgetName = "";

        for (BudgetCommand budget : budgetList) {

            LocalDate fromDate = LocalDate.parse(budget.getFrom(), userInputDateTimeFormatter);
            LocalDate toDate = LocalDate.parse(budget.getTo(), userInputDateTimeFormatter);

            if ((newDate.isEqual(fromDate) || newDate.isEqual(toDate) || (newDate
                    .isAfter(fromDate) && newDate.isBefore(toDate)))) {

                budgetName = budget.getName();

                break;
            }
        }

        return budgetName;
    }
}
