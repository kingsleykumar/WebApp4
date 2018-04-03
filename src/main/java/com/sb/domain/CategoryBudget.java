package com.sb.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Kingsley Kumar on 25/03/2018 at 22:23.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CategoryBudget {
    private String type;
    private String name;
    private String amount;
}