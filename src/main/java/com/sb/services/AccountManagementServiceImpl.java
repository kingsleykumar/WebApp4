package com.sb.services;

import com.sb.commands.PreferencesCommand;
import com.sb.commands.SignUpCommand;
import com.sb.domain.User;
import com.sb.domain.UserLocation;
import com.sb.repositories.BudgetRepository;
import com.sb.repositories.CategoryRepository;
import com.sb.repositories.TransactionRepository;
import com.sb.repositories.UserRepository;
import com.sb.services.containers.ResultMessage;
import com.sb.services.geolocation.GeoLocationRetriever;
import com.sb.services.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Created by Kingsley Kumar on 22/03/2018 at 14:51.
 */
@Slf4j
@Service
public class AccountManagementServiceImpl implements AccountManagementService {

    @Value("${context.param.geoLocationDBPath}")
    private String geoLocationDBPath;

    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private BudgetRepository budgetRepository;
    private CategoryService categoryService;
    private TransactionRepository transactionRepository;

    public AccountManagementServiceImpl(UserRepository userRepository,
                                        CategoryRepository categoryRepository,
                                        BudgetRepository budgetRepository,
                                        CategoryService categoryService,
                                        TransactionRepository transactionRepository) {

        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.budgetRepository = budgetRepository;
        this.categoryService = categoryService;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public ResultMessage createAccount(SignUpCommand signUpCommand, HttpServletRequest req) {

        String id = req.getParameter("id");

        if (id != null && !id.trim().isEmpty()) {

            log.info("SPAM ALERT !!!! value of id is entered as : " + id);

            return new ResultMessage(false,
                                     "Registration is not successful. Please email to support@spendbook.net for assistance.");
        }

        String email = signUpCommand.getEmail();
        char[] password = signUpCommand.getPassword() == null ? new char[0] : signUpCommand.getPassword()
                                                                                           .toCharArray();
        char[] confirmPassword = signUpCommand.getConfirmpassword() == null ? new char[0] : signUpCommand.getConfirmpassword()
                                                                                                         .toCharArray();

        if (email == null || email.isEmpty()) {

            return new ResultMessage(false, "Please enter E-mail.");
        }

        boolean valid = EmailValidator.getInstance().isValid(email);

        if (!valid) {

            return new ResultMessage(false, "Please enter a valid E-mail address.");
        }

        Optional<User> userOptional = userRepository.findByUsername(email);

        if (userOptional.isPresent()) {

            return new ResultMessage(false,
                                     "This email is already linked to an existing account.");
        }

        if (password.length == 0) {

            return new ResultMessage(false, "Please enter password.");
        }

        if (password.length < 8) {

            return new ResultMessage(false, "Password should contain at least 8 characters.");
        }

        if (confirmPassword.length == 0 || !Arrays.equals(password, confirmPassword)) {

            return new ResultMessage(false, "Confirm password field does not match the password field.");
        }

        String userId = String.valueOf(CommonUtils.uniqueCurrentTimeMS());

        String userName = email;

        ZonedDateTime localDateTime = LocalDateTime.now().atZone(ZoneId.of("UTC"));

        Date createdTime = new Date(localDateTime.toInstant()
                                                 .toEpochMilli());

        String key = Constants.SIGN_UP_KEY;

        char[] pwEncrypted = CryptoUtils.getEncryptedPwFromOriginal(key, String.valueOf(password)).toCharArray();

        String ipAddress = req.getRemoteAddr();

        String geoLocationDB = geoLocationDBPath;
//        String geoLocationDB = getServletContext().getInitParameter("geoLocationDBPath");

        log.info("geoLocationDB ==== " + geoLocationDB);

        File file = new File(geoLocationDB);

        Optional<UserLocation> userLocation = new GeoLocationRetriever().getLocation(ipAddress, file);

        log.info("ipAddress = " + ipAddress + " , userLocation = " + userLocation);

//        User user = new User(userId, userName, new String(pwEncrypted), "user", ipAddress, createdTime);
        User user = new User();
        user.setUserid(userId);
        user.setUsername(userName);
        user.setPassword(new String(pwEncrypted));
        user.setRoles("user");
        user.setIpaddress(ipAddress);
        user.setCreatedon(createdTime);

        if (userLocation.isPresent())
            user.setUserlocation(userLocation.get());

        User createdUser = userRepository.save(user);
//        boolean result = MongoManager.getInstance().getUsersDAO().createUser(user);

        log.info("user creation result " + createdUser);

        if (createdUser != null) { //TODO: Initialize with default categories when Category domain object is ready

            Map<String, String> categoryDescriptionMap = new HashMap<>();

            categoryDescriptionMap.put("Bills", "Bills such as Water, Electricity etc.");
            categoryDescriptionMap.put("Food", "Food related expenses");
            categoryDescriptionMap.put("Fuel", "Car, motorbike fuel expenses");
            categoryDescriptionMap.put("Clothes", "Clothes purchase expenses");
            categoryDescriptionMap.put("House Maintenance", "Expenses related to maintaining house");
            categoryDescriptionMap
                    .put("Vehicle Maintenance", "Expenses related to maintaining vehicles(car, motorbike etc.)");
            categoryDescriptionMap.put("Music", "Music albums purchase expenses");
            categoryDescriptionMap.put("Travel", "Travel expenses");
            categoryDescriptionMap.put("Fitness", "Gym membership and fitness related expenses");
            categoryDescriptionMap.put("Gadgets", "Gadgets purchase expenses");
            categoryDescriptionMap.put("Salary", "Salary income");
            categoryDescriptionMap.put("Banking", "Cashback, Interest, Account Charges etc.");
            categoryDescriptionMap.put("Charity", "Charity donations");

            categoryService.addDefaultCategories(userId, categoryDescriptionMap);

            return new ResultMessage(true, "Your account has been successfully created.");
        }

        return new ResultMessage(false,
                                 "Registration is not successful. Please email to support@spendbook.net for assistance.");
    }


    @Override
    public ResultMessage resetEmail(String email) {

        log.info("email = [" + email + "]");

        if (email == null || email.isEmpty()) {

            return new ResultMessage(false, "Please enter E-mail.");
        }

        boolean valid = EmailValidator.getInstance().isValid(email);

        if (!valid) {

            return new ResultMessage(false, "Please enter a valid E-mail address.");
        }

        Optional<User> userOptional = userRepository.findByUsername(email);

        if (!userOptional.isPresent()) {

            return new ResultMessage(false,
                                     "E-mail doesn't exist.");
        }

        User user = userOptional.get();

        String hash = CommonUtils.prepareRandomString(30);

        String userId = user.getUserid();

        updateEmailVerificationInfoInDBAndSendEmail(email, hash, userId);

        return new ResultMessage(true, "Email has been sent to reset the password.");
    }

    private void updateEmailVerificationInfoInDBAndSendEmail(String email, String hash, String userId) {

        Map<String, Object> fieldValueMap = new HashMap<>();

        fieldValueMap.put("pwResetEmailVerificationHash", BCrypt.hashpw(hash, Constants.SALT));
        fieldValueMap.put("pwResetEmailVerificationAttempt", 0);
        fieldValueMap.put("pwResetEmailVerificationStatus", Constants.PW_RESET_STATUS_EMAIL_LINK_SENT);

        userRepository.updateFields(userId, fieldValueMap);

        log.info("hash ==== " + hash);
        log.info("BCrypt.hashpw(hash,GlobalConstants.SALT) ==== " + BCrypt.hashpw(hash, Constants.SALT));

        new Thread(() -> {

            try {
                MailUtil.sendResetPasswordLink(userId, email, hash);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public Optional<String> verifyPasswordResetLink(HttpServletRequest req, HttpSession httpSession) {

        String userId = req.getParameter("userId");
        String hash = BCrypt.hashpw(req.getParameter("hash"), Constants.SALT);
        String scope = req.getParameter("scope");

        log.info("userId = " + userId);
        log.info("scope = " + scope);
        log.info("req.getParameter(\"hash\") = " + req.getParameter("hash"));
        log.info("hash = " + hash);

        String message = null;
        if (userId != null && hash != null && scope != null && scope.equals(Constants.RESET_PASSWORD)) {

            Optional<User> userOptional = userRepository.findByUserid(userId);

            if (userOptional.isPresent()) {

                User user = userOptional.get();

                String hashInDb = user.getPwResetEmailVerificationHash();

                log.info("hashInDb = " + hashInDb);

                if (hash.equals(hashInDb)) {

                    Map<String, Object> fieldValueMap = new HashMap<>();

                    fieldValueMap.put("pwResetEmailVerificationStatus", Constants.PW_RESET_STATUS_HASH_VERIFIED);

                    userRepository.updateFields(userId, fieldValueMap);

                    httpSession.setAttribute("resetpwuserid", userId);

                    message = Constants.PW_RESET_STATUS_HASH_VERIFIED;

//                    req.getRequestDispatcher("/WEB-INF/jsp/view_bs/resetpassword.jsp")
//                       .forward(req, resp);

                } else {

                    int attempts;

                    // increment attempt
                    try {
                        attempts = StringUtils.isBlank(user.getPwResetEmailVerificationAttempt()) ? 0 : Integer.valueOf(
                                user.getPwResetEmailVerificationAttempt());
                    } catch (NumberFormatException e) {
                        attempts = 0;
                    }

                    attempts++;

                    // save the attempt into db

                    Map<String, Object> fieldValueMap = new HashMap<>();

                    fieldValueMap.put("pwResetEmailVerificationAttempt", String.valueOf(attempts));

                    userRepository.updateFields(userId, fieldValueMap);

                    if (attempts == 20) {

                        // reset verification code if attempts equal to 20
                        String newHash = CommonUtils.prepareRandomString(30);

                        String email = user.getUsername();

                        updateEmailVerificationInfoInDBAndSendEmail(email, newHash, userId);

                        message = "Wrong reset link address was given for 20 times. A new link has been sent to your email.";

                    } else {

                        message = "Reset link address is incorrect. Please <html><body><a href=\"/resetemail\" id=\"link\">apply for reset</a> again.";
                    }

//                    if (message != null) {
//
//                        req.setAttribute("message", message);
//
//                        req.getRequestDispatcher("/WEB-INF/jsp/view_bs/message.jsp")
//                           .forward(req, resp);
//                    }
                }
            }
        }

        if (message == null) {

            message = "Reset link address is incorrect. Please <html><body><a href=\"/resetemail\" id=\"link\">apply for reset</a> again.";
        }

        return Optional.of(message);
    }

    @Override
    public ResultMessage resetPassword(char[] password, char[] confirmPassword, HttpSession httpSession) {

        if (password.length == 0) {

            return new ResultMessage(false, "Please enter password.");
        }

        if (password.length < 8) {

            return new ResultMessage(false, "Password should contain at least 8 characters.");
        }

        if (confirmPassword.length == 0 || !Arrays.equals(password, confirmPassword)) {

            return new ResultMessage(false, "Confirm password field does not match the password field.");
        }

        Object userIdObj = httpSession.getAttribute("resetpwuserid");

        String userId = userIdObj == null ? "" : userIdObj.toString();

        log.info("userId ########### = " + userId);

        if (userId.isEmpty()) {

            return new ResultMessage(false, "Incorrect Link.");
        }

        try {
            Map<String, Object> fieldValueMap = new HashMap<>();

            String key = Constants.SIGN_UP_KEY;

            String pwEncrypted = CryptoUtils.getEncryptedPwFromOriginal(key, new String(password));

            fieldValueMap.put("pwResetEmailVerificationHash", "");
            fieldValueMap.put("pwResetEmailVerificationAttempt", 0);
            fieldValueMap.put("pwResetEmailVerificationStatus", Constants.PW_RESET_STATUS_PW_CHANGED);
            fieldValueMap.put("password", pwEncrypted);

            userRepository.updateFields(userId, fieldValueMap);
        } catch (Exception e) {

            log.error("Exception while updating password.", e.getMessage());

            return new ResultMessage(false,
                                     "Error while updating password. Please email to support@spendbook.net for assistance.");

        }

        return new ResultMessage(true, "Password has been changed.");
    }

    @Override
    public ResultMessage updatePreferences(PreferencesCommand preferencesCommand, HttpSession httpSession) {

        Object userId = httpSession.getAttribute("userid");
        Object userName = httpSession.getAttribute("username");

//        log.info("userId === "+ userId);
//        log.info("userName === "+ userName);

        Map<String, Object> preferencesMap = new HashMap<>();
        preferencesMap.put("dateformat", preferencesCommand.getDateformat());
        preferencesMap.put("defaultBreakDownBy", preferencesCommand.getDefaultBreakDownBy());
        preferencesMap.put("defaultView", preferencesCommand.getDefaultView());
        preferencesMap.put("defaultChartType", preferencesCommand.getDefaultChartType());
        preferencesMap.put("defaultTableExpandState", preferencesCommand.getDefaultTableExpandState());
        preferencesMap.put("defaultPageAfterLogin", preferencesCommand.getDefaultPageAfterLogin());
        preferencesMap.put("defaultTimeRangeForTxView", preferencesCommand.getDefaultTimeRangeForTxView());

        boolean result = userRepository.updateFields(String.valueOf(userId), preferencesMap);

        log.info("result = " + result);

        if (result) {

            Optional<User> userOptional = userRepository.findByUsername(String.valueOf(userName));

            if (userOptional.isPresent()) {

                User user = userOptional.get();

                String dateFormatFromDB = user.getDateformat();

                String dateFormatJQuery = (dateFormatFromDB == null ? "dd/mm/yyyy" : dateFormatFromDB);

                String dateFormatJava = dateFormatFromDB.replace("mm", "MM");

//                DateTimeFormatter inputDateTimeFormatter = DateTimeFormatter.ofPattern(dateFormatJava);

                httpSession.setAttribute("dateFormatJQuery", dateFormatJQuery);
                httpSession.setAttribute("dateFormatJava", dateFormatJava);
                httpSession.setAttribute("defaultPageAfterLogin", user.getDefaultPageAfterLogin());
                httpSession.setAttribute("defaultTimeRangeForTxView", user.getDefaultTimeRangeForTxView());
//                httpSession.setAttribute("inputDateTimeFormatter", inputDateTimeFormatter);

                return new ResultMessage(true, "Preferences have been updated.");

            } else {

                return new ResultMessage(true, "Error happened while loading User Information from database.");
            }

        }

        return new ResultMessage(true, "Error happened while updating Preferences.");
    }

    @Override
    public ResultMessage changePassword(HttpServletRequest req, HttpSession httpSession) {

        log.info("ChangePasswordServlet.doPost");

        char[] currentPassword = req.getParameter("currentpassword") == null ? new char[0] : req
                .getParameter("currentpassword")
                .toCharArray();

        char[] newPassword = req.getParameter("password") == null ? new char[0] : req.getParameter("password")
                                                                                     .toCharArray();

        char[] confirmPassword = req.getParameter("confirmpassword") == null ? new char[0] : req
                .getParameter("confirmpassword").toCharArray();

        if (currentPassword.length == 0) {

            return new ResultMessage(false, "Please enter current password.");
        }

        if (newPassword.length == 0) {

            return new ResultMessage(false, "Please enter new password.");
        }

        if (newPassword.length < 8) {

            return new ResultMessage(false, "New password should contain at least 8 characters.");
        }

        if (confirmPassword.length == 0 || !Arrays.equals(newPassword, confirmPassword)) {

            return new ResultMessage(false, "Confirm new password field does not match the new password field.");
        }

        String userId = String.valueOf(httpSession.getAttribute("userid"));

        log.info("userId = " + userId);

        Optional<User> userOptional = userRepository.findByUserid(userId);

        if (!userOptional.isPresent()) {

            return new ResultMessage(false, "E-mail doesn't exist.");
        }

        User user = userOptional.get();

        char[] pwInDbArray = CryptoUtils.getOriginalPwFromEncrypted(Constants.SIGN_UP_KEY, user.getPassword())
                                        .toCharArray();

        boolean passwordMatches = Arrays.equals(currentPassword, pwInDbArray);

        if (!passwordMatches) {

            return new ResultMessage(false, "Incorrect current password.");
        }

        String pwEncrypted = CryptoUtils.getEncryptedPwFromOriginal(Constants.SIGN_UP_KEY, new String(newPassword));

        Map<String, Object> fieldValueMap = new HashMap<>();

        fieldValueMap.put("password", pwEncrypted);

        userRepository.updateFields(userId, fieldValueMap);

        return new ResultMessage(true, "Password has been changed.");
    }

    @Override
    public ResultMessage closeAccount(HttpServletRequest req, HttpSession httpSession) {

        char[] currentPassword = req.getParameter("currentpassword") == null ? new char[0] : req
                .getParameter("currentpassword")
                .toCharArray();

        if (currentPassword.length == 0) {

            return new ResultMessage(false, "Please enter your password.");
        }

        String userId = String.valueOf(httpSession.getAttribute("userid"));

        log.info("userId = " + userId);

        Optional<User> userOptional = userRepository.findByUserid(userId);

        if (!userOptional.isPresent()) {

            return new ResultMessage(false, "Account doesn't exist.");
        }

        User user = userOptional.get();

        char[] pwInDbArray = CryptoUtils.getOriginalPwFromEncrypted(Constants.SIGN_UP_KEY, user.getPassword())
                                        .toCharArray();

        boolean passwordMatches = Arrays.equals(currentPassword, pwInDbArray);

        if (!passwordMatches) {

            return new ResultMessage(false, "Password is not correct.");
        }

        boolean result = true;

        try {

            userRepository.deleteByUserid(userId);

            categoryRepository.deleteByUserId(userId);

            budgetRepository.deleteByUserId(userId);

            transactionRepository.deleteByUserId(userId);

        } catch (Throwable e) {

            log.error("Exception while closing account", e);

            result = false;
        }

        if (result)
            return new ResultMessage(true, "");
        else
            return new ResultMessage(false,
                                     "Unable to close your account. Please " + "<a href=\"contactus\" id=\"link\">" + "contact us </a>.");
    }
}
