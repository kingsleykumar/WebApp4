package com.sb.convertors;

import com.sb.commands.ContactUsCommand;
import com.sb.domain.ContactUs;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class ContactUsToContactUsCommandTest {

    private ContactUsToContactUsCommand converter;

    @Before
    public void setUp() throws Exception {

        converter = new ContactUsToContactUsCommand();
    }

    @Test
    public void convert() {

        //given
        ContactUs contactUs = new ContactUs(new Date(),
                                            "Kingsley",
                                            "kingsley.kumar@yahoo.co.uk",
                                            "Thank You");

        //when
        ContactUsCommand contactUsCommand = converter.convert(contactUs);

        //then
        Assert.assertEquals("Kingsley", contactUsCommand.getName());
        Assert.assertEquals("kingsley.kumar@yahoo.co.uk", contactUsCommand.getEmail());
        Assert.assertEquals("Thank You", contactUsCommand.getMessage());
    }
}