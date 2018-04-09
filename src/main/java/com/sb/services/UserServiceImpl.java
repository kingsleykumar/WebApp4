package com.sb.services;

import com.sb.commands.ContactUsCommand;
import com.sb.services.containers.ResultMessage;
import com.sb.convertors.ContactUsCommandToContactUs;
import com.sb.domain.ContactUs;
import com.sb.domain.CustomUserDetails;
import com.sb.domain.User;
import com.sb.repositories.ContactUsRepository;
import com.sb.repositories.UserRepository;
import com.sb.services.utils.CommonUtils;
import com.sb.services.utils.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Kingsley Kumar on 20/03/2018 at 09:55.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ContactUsRepository contactUsRepository;
    private ContactUsCommandToContactUs contactUsCommandToContactUs;

    public UserServiceImpl(UserRepository userRepository,
                           ContactUsRepository contactUsRepository,
                           ContactUsCommandToContactUs contactUsCommandToContactUs) {

        this.userRepository = userRepository;
        this.contactUsRepository = contactUsRepository;
        this.contactUsCommandToContactUs = contactUsCommandToContactUs;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<User> optionalUsers = userRepository.findByUsername(userName);

        if (!optionalUsers.isPresent()) {
            log.error("User " + userName + " doesn't exist!");
        }

//        log.info("######## optionalUsers.get() = " + optionalUsers.get());

        optionalUsers
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return optionalUsers
                .map(CustomUserDetails::new)
                .get();
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
        String userName = ((CustomUserDetails) authenticationSuccessEvent.getAuthentication()
                                                                         .getPrincipal()).getUsername();
        String userId = ((CustomUserDetails) authenticationSuccessEvent.getAuthentication()
                                                                       .getPrincipal()).getUserId();
        log.info(userName + " logged in Successfully!");
//        log.info(userId + "  logged in Successfully!");

        updateLoginTime(userId);
//        User user = this.userDao.findByLogin(userName);
//        user.setLastLoginDate(new Date());
    }


    @Override
    public void updateLoginTime(String userId) {

        Map<String, Object> fieldValueMap = new HashMap<>();

        fieldValueMap.put("lastlogin", CommonUtils.getCurrentDate());

        userRepository.updateFields(userId, fieldValueMap);
    }

    @Override
    public void updateLogoutTime(String userId) {

        Map<String, Object> fieldValueMap = new HashMap<>();

        fieldValueMap.put("lastlogout", CommonUtils.getCurrentDate());

        userRepository.updateFields(userId, fieldValueMap);
    }

    @Override
    public ResultMessage processContactUsMessage(ContactUsCommand contactUsCommand, HttpSession httpSession) {

        log.info("contactUsCommand = " + contactUsCommand);

        String id = contactUsCommand.getId();

        if (id != null && !id.trim().isEmpty()) {

            log.info("SPAM ALERT !!!! value of id is entered as : " + id);

            return new ResultMessage(false,
                                     "Unable to send your message. Please email to support@spendbook.net for assistance.");

        }

//        boolean valid = EmailValidator.getInstance().isValid(email);

        ContactUs contactUs = contactUsCommandToContactUs.convert(contactUsCommand);

        log.info("contactUs = " + contactUs);

        boolean result = true;

        try {
            contactUsRepository.save(contactUs);
        } catch (Exception e) {
            log.error("Exception while saving contact us object.", e.getMessage());
            result = false;
        }

        log.info("contact us entry creation result " + result);

        if (result) {

            Object userId = httpSession.getAttribute("userid");

            String userIdStr = (userId == null ? "" : String.valueOf(userId));

            new Thread(() -> {

                try {
                    MailUtil.sendEmailToSupport(contactUsCommand.getName(),
                                                userIdStr,
                                                contactUsCommand.getEmail(),
                                                contactUsCommand.getMessage());
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }).start();

            return new ResultMessage(true,
                                     "Thank you for contacting us. We'll get back to you as soon as possible.");
        }

        return new ResultMessage(false,
                                 "Unable to send your message. Please email to support@spendbook.net for assistance.");
    }

    @Override
    public Optional<User> findByUsername(String username) {

        Optional<User> optionalUser = userRepository.findByUsername(username);

        return optionalUser;
    }
}
