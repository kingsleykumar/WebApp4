package com.sb.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kingsley Kumar on 21/03/2018 at 19:45.
 */
@Slf4j
public class PostLoginURLRedirectionHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {


        log.info("Request Handler : " + httpServletRequest.getRequestURL());


        if (httpServletRequest.getRequestURL().toString().endsWith("/login")) {

            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/main");
        } else {

            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, httpServletRequest.getRequestURL().toString());
        }
    }
}
