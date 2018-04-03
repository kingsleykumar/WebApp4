package com.sb.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Kingsley Kumar on 21/03/2018 at 21:41.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SignUpCommand {

    private String email;
    private String password;
    private String confirmpassword;

}
