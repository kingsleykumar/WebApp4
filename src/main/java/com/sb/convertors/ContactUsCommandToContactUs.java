package com.sb.convertors;

import com.sb.commands.ContactUsCommand;
import com.sb.domain.ContactUs;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Kingsley Kumar on 23/03/2018 at 12:12.
 */
@Component
public class ContactUsCommandToContactUs implements Converter<ContactUsCommand, ContactUs> {
    @Override
    public ContactUs convert(ContactUsCommand contactUsCommand) {

        ContactUs contactUs = new ContactUs(new Date(),
                                            contactUsCommand.getName(),
                                            contactUsCommand.getEmail(),
                                            contactUsCommand.getMessage());

        return contactUs;
    }
}
