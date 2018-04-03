package com.sb.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Kingsley Kumar on 27/03/2018 at 21:55.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TransactionCommand {

    private String date;
    private String type;
    private String category;
    private String subcategory;
    private String item;
    private String value;
    private String id;
    private String budget;
    private boolean delete;
}
