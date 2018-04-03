package com.sb.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by Kingsley Kumar on 20/03/2018 at 12:26.
 */
@Getter
@Setter
@ToString
@Document(collection = "users")
public class User {

    @Id
    private ObjectId _id;
    private String userid;
    private String username;
    private String password;
    private String roles;
    private String ipaddress;
    private Date createdon;

    private Date lastlogin;
    private Date lastlogout;

    private String pwResetEmailVerificationHash;
    private String pwResetEmailVerificationAttempt;
    private String pwResetEmailVerificationStatus;

    private String dateformat;
    private String defaultChartType;
    private String defaultView;
    private String defaultBreakDownBy;
    private String defaultTableExpandState;
    private String defaultPageAfterLogin;
    private String defaultTimeRangeForTxView;

    private UserLocation userlocation;
}
