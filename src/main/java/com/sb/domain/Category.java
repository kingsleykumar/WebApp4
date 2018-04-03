package com.sb.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * Created by Kingsley Kumar on 24/03/2018 at 21:32.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "category")
public class Category {

    @Id
    private ObjectId _id;
    private String userId;
    private String category;
    private String description;
    private Date date;
    private List<String> subcategories;
}
