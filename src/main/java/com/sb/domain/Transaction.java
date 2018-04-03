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
 * Created by Kingsley Kumar on 27/03/2018 at 21:49.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "transaction")
public class Transaction {

    @Id
    private ObjectId _id;
    private String userId;
    private Date date;
    private List<TransactionContent> transactions = new ArrayList<>();
}
