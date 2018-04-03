package com.sb.services;

import com.sb.commands.PreferencesCommand;
import com.sb.services.containers.ResultMessage;
import com.sb.commands.SignUpCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public interface AccountManagementService {

    ResultMessage createAccount(SignUpCommand signUp, HttpServletRequest req);

    ResultMessage resetEmail(String email);

    Optional<String> verifyPasswordResetLink(HttpServletRequest req, HttpSession httpSession);

    ResultMessage resetPassword(char[] password, char[] confirmPassword, HttpSession httpSession);

    ResultMessage updatePreferences(PreferencesCommand signUp, HttpSession httpSession);

    ResultMessage changePassword(HttpServletRequest req, HttpSession httpSession);

    ResultMessage closeAccount(HttpServletRequest req, HttpSession httpSession);

}
