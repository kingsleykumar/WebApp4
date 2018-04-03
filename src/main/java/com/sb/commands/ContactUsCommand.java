package com.sb.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Created by Kingsley Kumar on 23/03/2018 at 11:41.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ContactUsCommand {

    @Size(min = 0, max = 50, message = "Name can not be more than 50 characters.")
    private String name;

    @NotEmpty(message = "Please enter E-mail.")
    @Email(message = "E-mail is not in valid format.")
    private String email;

    @Size(min = 0, max = 10000, message = "Message text can not be more than 10000 characters in length.")
    private String message;

    private String id;
    private String type;
}
