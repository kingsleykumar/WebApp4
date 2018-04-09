package com.sb.services;

import com.sb.commands.ContactUsCommand;
import com.sb.domain.User;
import com.sb.services.containers.ResultMessage;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Created by Kingsley Kumar on 21/03/2018 at 00:28.
 */
public interface UserService extends UserDetailsService, ApplicationListener<AuthenticationSuccessEvent> {

    void updateLoginTime(String userId);

    void updateLogoutTime(String userId);

    ResultMessage processContactUsMessage(ContactUsCommand contactUsCommand, HttpSession httpSession);

    Optional<User> findByUsername(String username);
}
