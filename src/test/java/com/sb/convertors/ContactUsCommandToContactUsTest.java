package com.sb.convertors;

import com.sb.commands.ContactUsCommand;
import com.sb.domain.ContactUs;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ContactUsCommandToContactUsTest {

    private ContactUsCommandToContactUs converter;

    @Before
    public void setUp() throws Exception {

        converter = new ContactUsCommandToContactUs();
    }

    @Test
    public void convert() {

        //given
        ContactUsCommand contactUsCommand = new ContactUsCommand();
        contactUsCommand.setEmail("kingsley.kumar@yahoo.co.uk");
        contactUsCommand.setName("Kingsley");
        contactUsCommand.setMessage("Thank You");

        //when
        ContactUs contactUs = converter.convert(contactUsCommand);

        //then
        Assert.assertNotNull(contactUs.getDate());
        Assert.assertEquals("kingsley.kumar@yahoo.co.uk", contactUs.getEmail());
        Assert.assertEquals("Kingsley", contactUs.getName());
        Assert.assertEquals("Thank You", contactUs.getMessage());
    }
}