package com.sb.convertors;

import com.sb.commands.ContactUsCommand;
import com.sb.domain.ContactUs;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by Kingsley Kumar on 23/03/2018 at 12:16.
 */
@Component
public class ContactUsToContactUsCommand implements Converter<ContactUs, ContactUsCommand>{

    @Override
    public ContactUsCommand convert(ContactUs contactUs) {

        ContactUsCommand contactUsCommand = new ContactUsCommand();

        contactUsCommand.setName(contactUs.getName());
        contactUsCommand.setEmail(contactUs.getEmail());
        contactUsCommand.setMessage(contactUs.getMessage());

        return contactUsCommand;
    }
}
