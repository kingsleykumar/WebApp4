package com.sb.controllers;

import com.sb.commands.BudgetCommand;
import com.sb.commands.CategoryBudgetCommand;
import com.sb.services.BudgetService;
import com.sb.services.CategoryService;
import com.sb.services.containers.ResultMessage;
import com.sb.services.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.sb.services.utils.Constants.TX_TYPE_EXPENSE;

/**
 * Created by Kingsley Kumar on 25/03/2018 at 22:11.
 */
@Slf4j
@Controller
public class BudgetController extends ControllerAbstract {

    private BudgetService budgetService;
    private CategoryService categoryService;

    public BudgetController(BudgetService budgetService, CategoryService categoryService) {
        this.budgetService = budgetService;
        this.categoryService = categoryService;
    }

    @GetMapping("/budget/list")
    public String getBudgetsListPage(Model model, HttpSession session) {

        log.info("---- BudgetController.getBudgetsListPage ----");

        List<BudgetCommand> budgetsList = budgetService.getAllBudgets(session);

        Collections.sort(budgetsList, getBudgetCommandComparator(session));

        model.addAttribute("budgets", budgetsList);

        model.addAttribute("title", "Budgets");

        return "view/listbudgets";
    }

    @GetMapping("/budget/add")
    public String getBudgetAddPage(Model model, HttpSession session) {

        log.info("---- BudgetController.getBudgetAddPage ----");

        List<String[]> categoryList = categoryService.getCategories(session);

        List<String> categories = getCategories(categoryList, true);

        model.addAttribute("transactionTypes", Constants.getTransactionTypes());
        model.addAttribute("categories", categories);
        model.addAttribute("actiontype", "Add");
        model.addAttribute("title", "Add Budget");

        String dateFormatJQuery = String.valueOf(session.getAttribute("dateFormatJQuery"));

        if (dateFormatJQuery == null) {

            dateFormatJQuery = "dd/mm/yyyy";
        }

        model.addAttribute("dateformat", dateFormatJQuery);

        BudgetCommand budgetCommand = new BudgetCommand();

        budgetCommand.setCategoryBudgets(Arrays.asList(new CategoryBudgetCommand(TX_TYPE_EXPENSE, "", "")));

//        budgetCommand.setName("Test");
//        budgetCommand.setAmount("1000");
//
//        budgetCommand.setCategoryBudgets(Arrays.asList(new CategoryBudgetCommand(Constants.TX_TYPE_EXPENSE, "Travel", "100"),
//                                                       new CategoryBudgetCommand(Constants.TX_TYPE_EXPENSE, "Gadgets", "200")));

        model.addAttribute("budgetcommand", budgetCommand);

        return "view/addeditbudget";
    }

    @PostMapping("/budget/add")
    public String handlePostFromBudgetAddPage(@ModelAttribute BudgetCommand budgetCommand,
                                              Model model,
                                              HttpSession session) {

        log.info("---- BudgetController.handlePostFromBudgetAddPage ----");

//        log.info("budgetCommand = " + budgetCommand);

        Optional<ResultMessage> resultMessageOptional = budgetService.addBudget(budgetCommand, session);

        if (resultMessageOptional.isPresent()) {

            log.info("Result Message : " + resultMessageOptional.get());
        }

        return "redirect:/budget/list";
    }

    @GetMapping("/budget/{budgetName}/edit")
    public String getBudgetUpdatePage(@PathVariable String budgetName, Model model, HttpSession session) {

        log.info("---- BudgetController.getBudgetUpdatePage ----");

        List<String[]> categoryList = categoryService.getCategories(session);

        List<String> categories = getCategories(categoryList, true);

        String dateFormatJQuery = String.valueOf(session.getAttribute("dateFormatJQuery"));

        if (dateFormatJQuery == null) {

            dateFormatJQuery = "dd/mm/yyyy";
        }

        Optional<BudgetCommand> budgetCommandOptional = budgetService.retrieveSpecificBudget(budgetName,
                                                                                             session);
        if (!budgetCommandOptional.isPresent()) {

            return "redirect:/budget/add";
        }

        BudgetCommand budgetCommand = budgetCommandOptional.get();

        if (budgetCommand.getCategoryBudgets().size() == 0) {

            budgetCommand.setCategoryBudgets(Arrays.asList(new CategoryBudgetCommand(TX_TYPE_EXPENSE, "", "")));
        }

        model.addAttribute("transactionTypes", Constants.getTransactionTypes());
        model.addAttribute("categories", categories);
        model.addAttribute("dateformat", dateFormatJQuery);
        model.addAttribute("actiontype", "Update");
        model.addAttribute("budgetName", budgetCommand.getName());
        model.addAttribute("title", "Edit Budget");
        model.addAttribute("budgetcommand", budgetCommand);

        return "view/addeditbudget";
    }

    @PostMapping("/budget/{budgetName}/edit")
    public String handlePostFromBudgetUpdatePage(
            @ModelAttribute BudgetCommand budgetCommand,
            Model model, HttpSession session) {

        log.info("---- BudgetController.handlePostFromBudgetUpdatePage ----");

        Optional<ResultMessage> resultMessageOptional = budgetService.updateBudget(budgetCommand, session);

        if (resultMessageOptional.isPresent()) {

            log.info("Result Message : " + resultMessageOptional.get());
        }

        return "redirect:/budget/list";
    }

    @GetMapping("/budget/{budgetName}/delete")
    public String deleteBudget(@PathVariable String budgetName,
                               Model model,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {

        log.info("---- BudgetController.deleteBudget ----");

        Optional<ResultMessage> resultMessage = budgetService.deleteBudget(budgetName, session);

        if (resultMessage.isPresent()) {

            redirectAttributes.addFlashAttribute("message", resultMessage.get().getMessage());
        }

        return "redirect:/budget/list";
    }


}
