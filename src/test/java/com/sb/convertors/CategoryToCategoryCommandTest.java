package com.sb.convertors;

import com.sb.commands.CategoryCommand;
import com.sb.domain.Category;
import com.sb.services.utils.Constants;
import com.sb.services.utils.CryptoException;
import com.sb.services.utils.CryptoUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CategoryToCategoryCommandTest {

    private CategoryToCategoryCommand convertor;

    @Before
    public void setUp() throws Exception {

        convertor = new CategoryToCategoryCommand();
    }

    @Test
    public void testEmptyObject() {

        assertNotNull(convertor.convert(new Category()));
    }

    @Test
    public void convert() throws CryptoException {

        //given
        Category category = new Category();
        category.setCategory(CryptoUtils.getEncryptedDataFromOriginal(Constants.CATEGORY_KEY, "Food"));
        category.setDescription(CryptoUtils.getEncryptedDataFromOriginal(Constants.CATEGORY_KEY, "Food related expenses"));
        category.setSubcategories(Arrays.asList(
                CryptoUtils.getEncryptedDataFromOriginal(Constants.CATEGORY_KEY, "Lunch"),
                CryptoUtils.getEncryptedDataFromOriginal(Constants.CATEGORY_KEY, "Groceries")
        ));

        //when
        CategoryCommand categoryCommand = convertor.convert(category);

        //then
        assertEquals("Food", categoryCommand.getCategory());
        assertEquals("Food related expenses", categoryCommand.getDescription());
        assertEquals("Lunch, Groceries", categoryCommand.getSubcategories());
    }
}