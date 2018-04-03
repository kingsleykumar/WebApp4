package com.sb.controllers;

import com.google.gson.Gson;
import com.sb.commands.BudgetCommand;
import com.sb.services.containers.ResultMessage;
import com.sb.services.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kingsley Kumar on 22/03/2018 at 19:40.
 */
@Slf4j
public class ControllerAbstract {

    protected boolean alreadyLoggedIn(HttpSession session) {

        Object userId = session.getAttribute("userid");
        Object userName = session.getAttribute("username");

        return (userId != null && userName != null &&
                !StringUtils.isBlank(String.valueOf(userId)) &&
                !StringUtils.isBlank(String.valueOf(userName)));
    }

    protected void sendResponse(ResultMessage resultMessage, HttpServletResponse resp) throws IOException {

        Gson gson = new Gson();

        String gsonString = gson.toJson(resultMessage);

        log.info("gsonString = " + gsonString);

        resp.getWriter().print(gsonString);
    }

    protected void printParameterMap(HttpServletRequest req) {

        req.getParameterMap()
           .keySet()
           .stream()
           .forEach(key ->
                            log.info("req.getParameterMap().get(s) = " + Arrays.asList(req.getParameterMap()
                                                                                          .get(key)) + " ; s = " + key));
    }

    protected void printSessionInfo(HttpSession httpSession) {

        try {
            Enumeration<String> enumeration = httpSession.getAttributeNames();
            while (enumeration.hasMoreElements()) {

                String elementName = enumeration.nextElement();

                log.info("Attribute / ----> " + elementName + ", value " + httpSession.getAttribute(elementName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected List<String> getCategories(List<String[]> categoryList, boolean includeEmptyOption) {

        List<String> categories = categoryList.stream()
                                              .map(row -> row[0])
                                              .sorted()
                                              .collect(Collectors.toList());

        if (includeEmptyOption)
            categories.add(0, "");

        return categories;
    }

    protected Comparator<BudgetCommand> getBudgetCommandComparator(HttpSession session) {

        DateTimeFormatter userInputDateTimeFormatter = CommonUtils.getDateTimeFormatter(session);

        return (o1, o2) -> {

            LocalDate toDate1 = LocalDate.parse(o1.getTo(), userInputDateTimeFormatter);

            LocalDate toDate2 = LocalDate.parse(o2.getTo(), userInputDateTimeFormatter);

            return toDate2.compareTo(toDate1);
        };
    }

}
