package com.sb.convertors;

import com.sb.commands.CategoryCommand;
import com.sb.domain.Category;
import com.sb.services.utils.CryptoException;
import org.junit.Before;
import org.junit.Test;

import static com.sb.services.utils.Constants.CATEGORY_KEY;
import static com.sb.services.utils.CryptoUtils.getEncryptedDataFromOriginal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CategoryCommandToCategoryTest {

    private CategoryCommandToCategory converter;

    @Before
    public void setUp() throws Exception {

        converter = new CategoryCommandToCategory();
    }

    @Test
    public void testEmptyObject() {

        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    public void convert() throws CryptoException {

        //given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setCategory("Food");
        categoryCommand.setSubcategories("Lunch, Groceries");
        categoryCommand.setDescription("Food related expenses");

        //when
        Category category = converter.convert(categoryCommand);

        //then
        assertEquals(getEncryptedDataFromOriginal(CATEGORY_KEY, categoryCommand.getCategory()), category.getCategory());
        assertEquals(2, category.getSubcategories().size());
        assertEquals(getEncryptedDataFromOriginal(CATEGORY_KEY, "Lunch"), category.getSubcategories().get(0));
        assertEquals(getEncryptedDataFromOriginal(CATEGORY_KEY, "Groceries"), category.getSubcategories().get(1));
        assertEquals(getEncryptedDataFromOriginal(CATEGORY_KEY, categoryCommand.getDescription()),
                     category.getDescription());
    }
}