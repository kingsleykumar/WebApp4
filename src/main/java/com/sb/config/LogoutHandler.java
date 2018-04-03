package com.sb.config;

import com.sb.domain.CustomUserDetails;
import com.sb.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kingsley Kumar on 20/03/2018 at 23:06.
 */
@Slf4j
@Component
public class LogoutHandler implements LogoutSuccessHandler {

    private UserService userService;

    public LogoutHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        if (authentication != null) {
            log.info(authentication.getName());
        }

        log.info("authentication.getPrincipal = " + authentication.getPrincipal());
        log.info("authentication.getDetails = " + authentication.getDetails());

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        userService.updateLogoutTime(userDetails.getUserId());

        //perform other required operation
        String URL = request.getContextPath() + "/";
        response.setStatus(HttpStatus.OK.value());
        response.sendRedirect(URL);
    }
}