package com.sb.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Created by Kingsley Kumar on 24/03/2018 at 21:36.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CategoryCommand {

    @NotEmpty(message = "Category Name is required.")
    @Size(max = 50, message = "Category Name can not be more than 50 characters in length.")
    private String category;

    @Size(max = 200, message = "Description can not be more than 200 characters in length.")
    private String description;

    @Size(min = 0, max = 5000, message = "Subcategories can not be more than 5000 characters in length.")
    private String subcategories;
}
