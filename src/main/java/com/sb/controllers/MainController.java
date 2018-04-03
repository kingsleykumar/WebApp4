package com.sb.controllers;

import com.sb.commands.ContactUsCommand;
import com.sb.services.UserService;
import com.sb.services.containers.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

import static com.sb.services.utils.Constants.*;

/**
 * Created by Kingsley Kumar on 19/03/2018 at 20:35.
 */
@Slf4j
@Controller
//@SessionAttributes("name")
public class MainController extends ControllerAbstract {

    private UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/main", "/"})
    public String getMainPage(Model model, HttpSession httpSession) {

        log.info("---- MainController.getMainPage ----");

        Object userName = httpSession.getAttribute("username");
        Object userId = httpSession.getAttribute("userid");

        log.info("userName = " + userName + " , userId = " + userId);

        model.addAttribute("username", userName != null ? String.valueOf(userName) : "");
        model.addAttribute("type", "");

        if (userName != null && userId != null &&
                !StringUtils.isBlank(String.valueOf(userId)) && !StringUtils.isBlank(String.valueOf(userName))) {

            return getDefaultPage(httpSession);
        }

        return "view/main";
    }

    @GetMapping("/main/{type}")
    public String getMainPage(@PathVariable String type, Model model, HttpSession httpSession) {

        log.info("---- MainController.getMainPage 2 ----");

        Object userName = httpSession.getAttribute("username");
        Object userId = httpSession.getAttribute("userid");

        log.info("userName = " + userName + " , userId = " + userId);
        log.info("typeObj = " + type);

        model.addAttribute("username", userName != null ? String.valueOf(userName) : "");
        model.addAttribute("type", type != null ? String.valueOf(type) : "");

        return "view/main";
    }

    @RequestMapping(value = "/postlogin", method = RequestMethod.GET)
    public String postLogin(HttpSession httpSession) {

        log.info("---- MainController.postLogin ----");

        printSessionInfo(httpSession);

        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout, HttpSession httpSession) {

        log.info("---- MainController.login ----");
//        log.info("error ======== " + error);

        printSessionInfo(httpSession);

        if (error != null) {
            model.addAttribute("errorMsg", "Invalid user name or password.");
        }

        if (logout != null) {
            model.addAttribute("msg", "You have been logged out successfully.");
        }

        return "login";
    }

    @RequestMapping(value = "/postlogout", method = RequestMethod.GET)
    public String postLogout(HttpSession httpSession) {

        log.info("---- MainController.postLogout ----");

        printSessionInfo(httpSession);

        return "redirect:/";
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String getWelcomePage(Model model, HttpSession httpSession) {

        log.info("---- MainController.getWelcomePage ----");

        model.addAttribute("message", "Welcome !!!");

        printSessionInfo(httpSession);

        return "welcome";
    }

    @GetMapping("/contactus")
    public String getContactUsPage(Model model, HttpSession httpSession) {

        log.info("---- MainController.getContactUsPage ----");

        applyUpdateForContactUsPage("", model, httpSession);

        return "view/contactus";
    }

    @GetMapping("/contactus/{type}")
    public String getContactUsPage(@PathVariable String type, Model model, HttpSession httpSession) {

        log.info("---- MainController.getContactUsPage 2 ----");

        applyUpdateForContactUsPage(type, model, httpSession);

        return "view/contactus";
    }

    private void applyUpdateForContactUsPage(@PathVariable String type, Model model, HttpSession httpSession) {

        Object userName = httpSession.getAttribute("username");
        Object userId = httpSession.getAttribute("userid");

        ContactUsCommand contactUsCommand = new ContactUsCommand();
        if (userName != null && !StringUtils.isBlank(String.valueOf(userName))) {
            contactUsCommand.setEmail(String.valueOf(userName));
        }

        if (userName == null & !StringUtils.isBlank(type)) {
            type = "";
        }

        contactUsCommand.setType(type);

        log.info("userName = " + userName + " , userId = " + userId);
        log.info("typeObj = " + type);

        model.addAttribute("title", "Contact Us");
        model.addAttribute("username", userName != null ? userName.toString() : "");
        model.addAttribute("type", type);
        model.addAttribute("contactus", contactUsCommand);
    }

    @PostMapping("/contactus")
    public String handlePostFromContactUsPage(@Valid @ModelAttribute("contactus") ContactUsCommand contactus,
                                              BindingResult bindingResult,
                                              Model model,
                                              HttpServletRequest req,
                                              HttpServletResponse resp,
                                              HttpSession httpSession,
                                              RedirectAttributes redirectAttributes) throws IOException {

        log.info("---- MainController.handlePostFromContactUsPage ----");

        model.addAttribute("type", contactus.getType());

        if (bindingResult.hasErrors()) {

            return "view/contactus";
        }

        ResultMessage resultMessage = userService.processContactUsMessage(contactus, httpSession);

        redirectAttributes.addFlashAttribute("type", contactus.getType());

        redirectAttributes.addFlashAttribute("message", resultMessage.getMessage());

        return "redirect:/message";
    }

    @GetMapping("/message")
    public String getMessagePage(@ModelAttribute("message") String message,
                                 HttpServletRequest req) {

        log.info("---- MainController.getMessagePage ----");

        printParameterMap(req);

        log.info("message = " + message);

        if (StringUtils.isBlank(message)) {

            return "redirect:/";
        }

        return "view/message";
    }

    private String getDefaultPage(HttpSession httpSession) {

        String defaultPageAfterLogin = String.valueOf(httpSession.getAttribute("defaultPageAfterLogin"));

        String url;

        switch (defaultPageAfterLogin) {

            case PAGE_CATEGORIES:
                url = "redirect:/category/list";
                break;

            case PAGE_BUDGETS:
                url = "redirect:/budget/list";
                break;

            case PAGE_ADD_TRANSACTION:
                url = "redirect:/transaction/add";
                break;

            case PAGE_VIEW_EDIT_TRANSACTION:
                url = "redirect:/transaction/view";
                break;

            case PAGE_VIEW_SUMMARY:
                url = "redirect:/summary/view";
                break;

            default:
                url = "view/main";
        }

        return url;
    }
}
