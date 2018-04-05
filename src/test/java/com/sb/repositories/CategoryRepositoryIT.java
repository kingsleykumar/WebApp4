package com.sb.repositories;

import com.sb.domain.Category;
import com.sb.services.utils.Constants;
import com.sb.services.utils.CryptoException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static com.sb.services.utils.CryptoUtils.getEncryptedDataFromOriginal;

@Slf4j
@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryRepositoryIT {

    @Autowired
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByUserIdAndCategory() throws CryptoException {

        String categoryName = getEncryptedDataFromOriginal(Constants.CATEGORY_KEY, "Food");

        Optional<Category> categoryOptional = categoryRepository.findByUserIdAndCategory("1521742579105", categoryName);

        Category category = categoryOptional.get();

        Assert.assertNotNull(category);

        Assert.assertEquals(categoryName, category.getCategory());
    }

    @Test
    public void findAllByUserId() {

        Optional<List<Category>> categoryOptional = categoryRepository.findAllByUserId("1521742579105");

        List<Category> categoryList = categoryOptional.get();

        Assert.assertNotNull(categoryList);
        Assert.assertTrue(categoryList.size() > 0);
    }

}