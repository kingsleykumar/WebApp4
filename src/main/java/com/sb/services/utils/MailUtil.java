package com.sb.services.utils;

import org.apache.commons.lang3.StringUtils;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Kingsley Kumar on 16/11/2016.
 */
public class MailUtil {

    public static void sendResetPasswordLink(String id,
                                             String email,
                                             String hash) throws javax.mail.MessagingException {

    }

    public static void sendEmailToSupport(String name,
                                          String id,
                                          String email,
                                          String content) throws javax.mail.MessagingException {


    }
}