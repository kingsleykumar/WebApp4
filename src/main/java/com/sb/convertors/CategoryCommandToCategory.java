package com.sb.convertors;

import com.sb.commands.CategoryCommand;
import com.sb.domain.Category;
import com.sb.services.utils.CryptoException;
import com.sb.services.utils.CryptoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.sb.services.utils.Constants.CATEGORY_KEY;

/**
 * Created by Kingsley Kumar on 24/03/2018 at 21:46.
 */
@Slf4j
@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Override
    public Category convert(CategoryCommand categoryCommand) {

        Category category = new Category();

        try {
            String categoryName = categoryCommand.getCategory();
            String categoryNameEncrypted = CryptoUtils.getEncryptedDataFromOriginal(CATEGORY_KEY, categoryName);

            String description = categoryCommand.getDescription();
            String descriptionEncrypted = CryptoUtils.getEncryptedDataFromOriginal(CATEGORY_KEY, description);

            String subCategories = categoryCommand.getSubcategories();

            List<String> subcategoriesList = new ArrayList<>();

            if (subCategories != null && !subCategories.trim().isEmpty()) {

                String[] subCategoriesArray = subCategories.split(",");

                List<String> subCategoriesUnFiltered = Arrays.stream(subCategoriesArray)
                                                             .map(e -> e.trim())
                                                             .collect(Collectors.toList());
                subCategoriesUnFiltered.forEach(e -> {
                    if (!subcategoriesList.contains(e)) {

                        subcategoriesList.add(e);
                    }
                });
            }

            List<String> subCategoriesEncrypted = new ArrayList<>();

            for (String subCategory : subcategoriesList) {

                subCategoriesEncrypted.add(CryptoUtils.getEncryptedDataFromOriginal(CATEGORY_KEY, subCategory));
            }

            category.setCategory(categoryNameEncrypted);
            category.setDescription(descriptionEncrypted);
            category.setSubcategories(subCategoriesEncrypted);

        } catch (CryptoException | NullPointerException e) {
            log.error("Error while converting CategoryCommand to Category.", e.getMessage());
        }

        return category;
    }
}
