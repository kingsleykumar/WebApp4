package com.sb.convertors;

import com.sb.commands.CategoryCommand;
import com.sb.domain.Category;
import com.sb.services.utils.CryptoException;
import com.sb.services.utils.CryptoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sb.services.utils.Constants.CATEGORY_KEY;

/**
 * Created by Kingsley Kumar on 24/03/2018 at 22:11.
 */
@Slf4j
@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Override
    public CategoryCommand convert(Category category) {

        CategoryCommand categoryCommand = new CategoryCommand();

        try {
            String categoryName = category.getCategory();
            String categoryNameDecrypted = CryptoUtils.getOriginalDataFromEncrypted(CATEGORY_KEY, categoryName);

            String description = category.getDescription();
            String descriptionDecrypted = "";

            if (!StringUtils.isBlank(description)) {
                descriptionDecrypted = CryptoUtils.getOriginalDataFromEncrypted(CATEGORY_KEY, description);
            }

            List<String> subCategoriesList = category.getSubcategories();
            List<String> subCategoriesDecrypted = new ArrayList<>();

            if (subCategoriesList != null) {

                for (String subCategory : subCategoriesList) {

                    subCategoriesDecrypted.add(CryptoUtils.getOriginalDataFromEncrypted(CATEGORY_KEY, subCategory));
                }
            }

            String subCategories = getSubCategoriesAsString(subCategoriesDecrypted);

            categoryCommand.setCategory(categoryNameDecrypted);
            categoryCommand.setSubcategories(subCategories);
            categoryCommand.setDescription(descriptionDecrypted);

        } catch (CryptoException | NullPointerException e) {
            log.error("Error while converting CategoryCommand to Category.", e.getMessage());
        }

        return categoryCommand;
    }

    private String getSubCategoriesAsString(List<String> subCategories) {

        if (subCategories != null && subCategories.size() > 0) {

            if (subCategories.size() == 1) {

                return subCategories.get(0);
            } else {

                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append(subCategories.get(0));
                stringBuilder.append(",");

                stringBuilder.append(subCategories.stream()
                                                  .skip(1)
                                                  .map(e -> String.valueOf(" " + e))
                                                  .collect(Collectors.joining(",")));

                return stringBuilder.toString();
            }
        }

        return "";
    }
}
