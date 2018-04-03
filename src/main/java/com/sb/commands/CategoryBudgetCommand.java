package com.sb.commands;

import lombok.*;

/**
 * Created by Kingsley Kumar on 25/03/2018 at 22:17.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryBudgetCommand {
    private String type;
    private String name;
    private String amount;
}
