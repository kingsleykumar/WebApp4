package com.sb.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kingsley Kumar on 25/03/2018 at 22:16.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BudgetCommand {

    private String name;
    private String amount;
    private String from;
    private String to;
    private List<CategoryBudgetCommand> categoryBudgets = new ArrayList<>();
}
