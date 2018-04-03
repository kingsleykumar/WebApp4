package com.sb.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kingsley Kumar on 25/03/2018 at 22:20.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "budget")
public class Budget {

    @Id
    private ObjectId _id;
    private String userId;

    private String name;
    private String amount;
    private Date from;
    private Date to;
    private List<CategoryBudget> categorybudgets = new ArrayList<>();
}
