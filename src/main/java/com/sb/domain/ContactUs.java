package com.sb.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import java.util.Date;

/**
 * Created by Kingsley Kumar on 23/03/2018 at 11:42.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Document(collection = "conactus")
public class ContactUs {

    @Id
    private ObjectId _id;
    private final Date date;
    private final String name;
    private final String email;
    private final String message;
}
