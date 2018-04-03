package com.sb.config;

import com.sb.domain.CustomUserDetails;
import com.sb.services.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Kingsley Kumar on 20/03/2018 at 19:30.
 */
@Slf4j
public class RequestFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

//        log.info("Current Thread : " + Thread.currentThread().getName());
//        log.info("---- MainController.doFilter ----");

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession(false);

//        log.info("session = " + session);

        if (session != null && (session.getAttribute("username") == null)) {

            Object sciObj = session.getAttribute("SPRING_SECURITY_CONTEXT");

            if (sciObj != null) {

                SecurityContextImpl sci = (SecurityContextImpl) sciObj;

                CustomUserDetails cud = (CustomUserDetails) sci.getAuthentication()
                                                               .getPrincipal();
                String dateFormat = cud.getDateFormat();

                String dateFormatJQuery = (dateFormat == null ? "dd/mm/yyyy" : dateFormat);

                String dateFormatJava = dateFormatJQuery.replace("mm", "MM");

                String defaultPageAfterLogin = (StringUtils.isBlank(cud.getDefaultPageAfterLogin()) ? Constants.DEFAULT_PAGE_AFTER_LOGIN : cud
                        .getDefaultPageAfterLogin());

                String defaultTimeRangeForTxView = (StringUtils.isBlank(cud.getDefaultTimeRangeForTxView()) ? Constants.DEFAULT_TIME_RANGE : cud
                        .getDefaultTimeRangeForTxView());

//                DateTimeFormatter inputDateTimeFormatter = DateTimeFormatter.ofPattern(dateFormatJava);

                log.info("cud.getClass = " + cud.getClass());
                log.info("cud = " + cud.getUsername());
                log.info("cud.toString() = " + cud.toString());

                session.setAttribute("username", cud.getUsername());
                session.setAttribute("userid", cud.getUserId());

                session.setAttribute("dateFormatJQuery", dateFormatJQuery);
                session.setAttribute("dateFormatJava", dateFormatJava);
                session.setAttribute("defaultPageAfterLogin", defaultPageAfterLogin);
                session.setAttribute("defaultTimeRangeForTxView", defaultTimeRangeForTxView);
//                session.setAttribute("inputDateTimeFormatter", inputDateTimeFormatter);
            }
        }

        chain.doFilter(request, response);
    }
}