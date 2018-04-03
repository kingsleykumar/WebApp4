package com.sb.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by Kingsley Kumar on 27/03/2018 at 21:50.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TransactionContent {

    private String category;
    private String type;
    private String subcategory;
    private String item;
    private String value;
    private String by;

    @Field("id")
    private Integer id;
    private String budget;
}
