package com.sb.controllers;

import com.google.gson.Gson;
import com.sb.commands.BudgetCommand;
import com.sb.domain.User;
import com.sb.repositories.UserRepository;
import com.sb.services.BudgetService;
import com.sb.services.SummaryService;
import com.sb.services.calculation.ResultNode;
import com.sb.services.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by Kingsley Kumar on 30/03/2018 at 18:11.
 */
@Slf4j
@Controller
public class SummaryController extends ControllerAbstract {

    private UserRepository userRepository;
    private SummaryService summaryService;
    private BudgetService budgetService;

    public SummaryController(UserRepository userRepository,
                             SummaryService summaryService,
                             BudgetService budgetService) {

        this.userRepository = userRepository;
        this.summaryService = summaryService;
        this.budgetService = budgetService;
    }

    @GetMapping("/summary/view")
    public String getSummaryPage(Model model, HttpSession httpSession) {

        return getSummaryPagePerBreakdown(model, httpSession, null);
    }

    private String getSummaryPagePerBreakdown(Model model, HttpSession httpSession, String breakdownBy) {

        String selectedDateFormat = String.valueOf(httpSession.getAttribute("dateFormatJQuery"));

        String userName = String.valueOf(httpSession.getAttribute("username"));

        Optional<User> userOptional = userRepository.findByUsername(userName);

        User user = userOptional.orElse(new User());

        String defaultBreakDownBy;

        if (breakdownBy != null) {
            defaultBreakDownBy = breakdownBy;
        } else {
            defaultBreakDownBy = user.getDefaultBreakDownBy();
        }

        String defaultView = user.getDefaultView();
        String defaultChartType = user.getDefaultChartType();
        String defaultTableExpandState = user.getDefaultTableExpandState();

        if (defaultBreakDownBy == null)
            defaultBreakDownBy = "monthly";

        if (defaultView == null)
            defaultView = "bothViews";

        if (defaultChartType == null)
            defaultChartType = "pie";

        if (defaultTableExpandState == null)
            defaultTableExpandState = "expandToTwoLevels";

        model.addAttribute("breakDownByList", Constants.getSummaryBreakDownByList());
        model.addAttribute("chartTypeList", Constants.getSummaryChartTypesList());
        model.addAttribute("dateformat", selectedDateFormat);
        model.addAttribute("title", "Summary");

        model.addAttribute("defaultBreakDownBy", defaultBreakDownBy);
        model.addAttribute("defaultView", defaultView);
        model.addAttribute("defaultChartType", defaultChartType);
        model.addAttribute("defaultTableExpandState", defaultTableExpandState);

        return "view/viewsummary";
    }

    @GetMapping("/summary/retrieve")
    public void retrieveSummaryPageData(HttpServletRequest req,
                                        HttpServletResponse resp,
                                        HttpSession httpSession) throws IOException {

        List<ResultNode> resultNodes = summaryService.retrieveSummary(req, httpSession);

        Gson gson = new Gson();

        log.info("gson.toJson(resultNodes) = " + gson.toJson(resultNodes));

        resp.getWriter()
            .print(gson.toJson(resultNodes));
    }

    @GetMapping("/summary/{budgetName}/view")
    public String getSummaryPageForBudget(@PathVariable String budgetName, Model model, HttpSession httpSession) {

        Optional<BudgetCommand> budgetCommandOptional = budgetService.retrieveSpecificBudget(budgetName,
                                                                                             httpSession);

        if (budgetCommandOptional.isPresent()) {

            BudgetCommand budgetCommand = budgetCommandOptional.get();

            String from = budgetCommand.getFrom();

            String to = budgetCommand.getTo();

            model.addAttribute("fromDefault", from);

            model.addAttribute("toDefault", to);

            return getSummaryPagePerBreakdown(model, httpSession, Constants.BUDGET_ID);
        }

        return getSummaryPagePerBreakdown(model, httpSession, null);
    }
}
