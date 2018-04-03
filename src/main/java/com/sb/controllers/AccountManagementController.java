package com.sb.controllers;

import com.sb.commands.PreferencesCommand;
import com.sb.commands.SignUpCommand;
import com.sb.domain.User;
import com.sb.repositories.UserRepository;
import com.sb.services.AccountManagementService;
import com.sb.services.containers.ResultMessage;
import com.sb.services.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by Kingsley Kumar on 21/03/2018 at 21:33.
 */
@Slf4j
@Controller
public class AccountManagementController extends ControllerAbstract {

    private AccountManagementService accountManagementService;
    private UserRepository userRepository;

    public AccountManagementController(AccountManagementService accountManagementService,
                                       UserRepository userRepository) {
        this.accountManagementService = accountManagementService;
        this.userRepository = userRepository;
    }

    @GetMapping("/signup")
    public String getSignUpPage(Model model, HttpSession httpSession) {

        log.info("---- MainController.getSignUpPage ----");

        if (alreadyLoggedIn(httpSession)) {

            return "redirect:/main";
        }

        SignUpCommand signUpCommand = new SignUpCommand();

        model.addAttribute("signup", signUpCommand);

        return "view/signup";
    }

    @PostMapping("/signup")
    public void handlePostFromSignUpPage(@ModelAttribute SignUpCommand signup,
                                         HttpServletRequest req,
                                         HttpServletResponse resp) throws IOException {

        log.info("---- MainController.handlePostFromSignUpPage ----");
        log.info("signup = " + signup);

        printParameterMap(req);

        ResultMessage resultMessage = accountManagementService.createAccount(signup, req);

        sendResponse(resultMessage, resp);
    }

    @GetMapping("/resetemail")
    public String getResetEmailPage(HttpSession httpSession) {

        log.info("---- MainController.getResetEmailPage ----");

        if (alreadyLoggedIn(httpSession)) {

            return "redirect:/main";
        }

        return "view/resetemail";
    }

    @PostMapping("/resetemail")
    public void handlePostFromResetEmailPage(@RequestParam String email, HttpServletResponse resp) throws IOException {

        log.info("---- MainController.handlePostFromResetEmailPage ----");

        ResultMessage resultMessage = accountManagementService.resetEmail(email);

        sendResponse(resultMessage, resp);
    }

    @GetMapping("/verifyemail")
    public String handleVerifyEmail(Model model, HttpServletRequest request, HttpSession httpSession) {

        log.info("---- MainController.handleVerifyEmail ----");

        Optional<String> messageOptional = accountManagementService.verifyPasswordResetLink(request, httpSession);

        String message = messageOptional.get();

        log.info("message = " + message);

        if (message.equals(Constants.PW_RESET_STATUS_HASH_VERIFIED)) {

            return "view/resetpassword";
        } else {

            model.addAttribute("message", message);

            return "view/message";
        }
    }

    @PostMapping("/resetpassword")
    public void handlePostFromResetPasswordPage(@RequestParam String password,
                                                @RequestParam String confirmpassword,
                                                HttpServletResponse resp,
                                                HttpSession httpSession) throws IOException {

        log.info("---- MainController.handlePostFromResetPasswordPage ----");

        char[] passwordArray = (password == null ? new char[0] : password.toCharArray());

        char[] confirmPasswordArray = (confirmpassword == null ? new char[0] : confirmpassword.toCharArray());

        ResultMessage resultMessage = accountManagementService.resetPassword(passwordArray,
                                                                             confirmPasswordArray,
                                                                             httpSession);

        sendResponse(resultMessage, resp);
    }

    @GetMapping("/preferences")
    public String getPreferencesPage(Model model, HttpSession httpSession) {

        log.info("---- MainController.getPreferencesPage ----");

        model.addAttribute("supportedFormats", Constants.getSupportedDateFormats());
        model.addAttribute("breakDownByList", Constants.getSummaryBreakDownByList());
        model.addAttribute("summaryViewsList", Constants.getSummaryViewsList());
        model.addAttribute("chartTypeList", Constants.getSummaryChartTypesList());
        model.addAttribute("tableExpandStateList", Constants.getSummaryTableExpandStateList());
        model.addAttribute("pagesList", Constants.getPagesList());
        model.addAttribute("timeRangeList", Constants.getTimeRangeOptions());

        String selectedDateFormat = String.valueOf(httpSession.getAttribute("dateFormatJQuery"));

        String userName = String.valueOf(httpSession.getAttribute("username"));

        Optional<User> userOptional = userRepository.findByUsername(userName);

        User user = userOptional.orElse(new User());

//        log.info("user = " + user);

        String defaultBreakDownBy = user.getDefaultBreakDownBy();
        String defaultView = user.getDefaultView();
        String defaultChartType = user.getDefaultChartType();
        String defaultTableExpandState = user.getDefaultTableExpandState();
        String defaultPageAfterLogin = user.getDefaultPageAfterLogin();
        String defaultTimeRangeForTxView = user.getDefaultTimeRangeForTxView();

        if (defaultBreakDownBy == null)
            defaultBreakDownBy = "monthly";

        if (defaultView == null)
            defaultView = "bothViews";

        if (defaultChartType == null)
            defaultChartType = "pie";

        if (defaultTableExpandState == null)
            defaultTableExpandState = "expandToTwoLevels";

        if (defaultPageAfterLogin == null)
            defaultPageAfterLogin = Constants.DEFAULT_PAGE_AFTER_LOGIN;

        if (defaultTimeRangeForTxView == null)
            defaultTimeRangeForTxView = Constants.DEFAULT_TIME_RANGE;

        model.addAttribute("title", "Preferences");

        PreferencesCommand preferencesCommand = new PreferencesCommand();
        preferencesCommand.setDateformat(selectedDateFormat);
        preferencesCommand.setDefaultBreakDownBy(defaultBreakDownBy);
        preferencesCommand.setDefaultView(defaultView);
        preferencesCommand.setDefaultChartType(defaultChartType);
        preferencesCommand.setDefaultTableExpandState(defaultTableExpandState);
        preferencesCommand.setDefaultPageAfterLogin(defaultPageAfterLogin);
        preferencesCommand.setDefaultTimeRangeForTxView(defaultTimeRangeForTxView);

        model.addAttribute("preferences", preferencesCommand);

        return "view/preferences";
    }

    @PostMapping("/preferences")
    public void handlePostFromPreferences(@ModelAttribute PreferencesCommand preferences,
                                          HttpSession httpSession,
                                          HttpServletRequest req,
                                          HttpServletResponse resp) throws IOException {

        log.info("---- MainController.handlePostFromPreferences ----");
//        log.info("preferences = " + preferences);

        printParameterMap(req);

        ResultMessage resultMessage = accountManagementService.updatePreferences(preferences, httpSession);

        sendResponse(resultMessage, resp);
    }


    @GetMapping("/changepassword")
    public String getChangePasswordPage(Model model) {

        model.addAttribute("title", "Change Password");

        return "view/changepassword";
    }

    @PostMapping("/changepassword")
    public void handlePostFromChangePasswordPage(Model model,
                                                 HttpServletRequest req,
                                                 HttpServletResponse resp,
                                                 HttpSession httpSession) throws IOException {

        model.addAttribute("title", "Change Password");

        ResultMessage resultMessage = accountManagementService.changePassword(req, httpSession);

        sendResponse(resultMessage, resp);
    }


    @GetMapping("/closeaccount")
    public String getCloseAccountPage(Model model,
                                      HttpServletRequest req,
                                      HttpSession httpSession) throws ServletException {

        model.addAttribute("title", "Close Account");

        String result = req.getParameter("result");

        if (result == null) {

            model.addAttribute("title", "Close Account");

            return "view/closeaccount";

        } else {

            String message;

            if ("true".equals(result)) {

                message = "Your account has been closed successfully. We welcome your " + "<a href=\"contactus\" id=\"link\">" + "feedback </a>" + ". Your feedback will help us to improve the site.";

                req.logout();

                httpSession.invalidate();

                SecurityContextHolder.clearContext();

            } else {

                message = "Unable to close your account. Please " + "<a href=\"contactus\" id=\"link\">" + "contact us </a>.";
            }

            model.addAttribute("message", message);

            return "view/message";
        }
    }

    @PostMapping("/closeaccount")
    public void handlePostFromCloseAccountPage(Model model,
                                               HttpServletRequest req,
                                               HttpServletResponse resp,
                                               HttpSession httpSession) throws IOException {

        ResultMessage resultMessage = accountManagementService.closeAccount(req, httpSession);

        sendResponse(resultMessage, resp);
    }
}
