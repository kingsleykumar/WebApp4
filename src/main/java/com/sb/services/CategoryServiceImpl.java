package com.sb.services;

import com.sb.commands.CategoryCommand;
import com.sb.convertors.CategoryCommandToCategory;
import com.sb.convertors.CategoryToCategoryCommand;
import com.sb.domain.Category;
import com.sb.repositories.CategoryRepository;
import com.sb.repositories.TransactionRepository;
import com.sb.services.containers.ResultMessage;
import com.sb.services.utils.Constants;
import com.sb.services.utils.CryptoException;
import com.sb.services.utils.CryptoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Kingsley Kumar on 24/03/2018 at 22:59.
 */
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryCommandToCategory categoryCommandToCategory;
    private CategoryToCategoryCommand categoryToCategoryCommand;
    private TransactionRepository transactionRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryCommandToCategory categoryCommandToCategory,
                               CategoryToCategoryCommand categoryToCategoryCommand,
                               TransactionRepository transactionRepository) {

        this.categoryRepository = categoryRepository;
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Optional<ResultMessage> addCategory(CategoryCommand categoryCommand, HttpSession session) {

        Object userId = session.getAttribute("userid");

        Category category = categoryCommandToCategory.convert(categoryCommand);

        String categoryName = category.getCategory();

        Optional<Category> categoryFromDb = categoryRepository.findByUserIdAndCategory(String.valueOf(userId),
                                                                                       categoryName);

        if (categoryFromDb.isPresent()) {

            return Optional.of(new ResultMessage(false, "Category already exists. Please enter different name."));
        }

        category.setUserId(String.valueOf(userId));

        category.setDate(new Date());

        Category categorySaved = categoryRepository.save(category);

        log.info("categorySaved id = " + categorySaved.get_id());

        return Optional.empty();
    }

    @Override
    public Optional<ResultMessage> updateCategory(CategoryCommand categoryCommand, HttpSession session) {

        Object userId = session.getAttribute("userid");

        Category category = categoryCommandToCategory.convert(categoryCommand);

        String categoryName = category.getCategory();

        Optional<Category> categoryFromDb = categoryRepository.findByUserIdAndCategory(String.valueOf(userId),
                                                                                       categoryName);
        if (categoryFromDb.isPresent()) {

            category.set_id(categoryFromDb.get().get_id());

            category.setDate(categoryFromDb.get().getDate());
        } else {

            category.setDate(new Date());
        }

        category.setUserId(String.valueOf(userId));

        Category categorySaved = categoryRepository.save(category);

        log.info("categorySaved id = " + categorySaved.get_id());

        return Optional.empty();
    }

    @Override
    public Optional<ResultMessage> deleteCategory(String categoryName, HttpSession session) {

        Object userId = session.getAttribute("userid");

        String categoryNameEncrypted = categoryName;
        try {
            categoryNameEncrypted = CryptoUtils.getEncryptedDataFromOriginal(Constants.TX_KEY, categoryName);
        } catch (CryptoException e) {
            log.error("Exception while encrypting category name.", e.getMessage());
        }

        boolean anyTxExistsForTheCategory = transactionRepository.anyTransactionExistsForCategory(String.valueOf(userId),
                                                                                                  categoryNameEncrypted);

        if (anyTxExistsForTheCategory) {

            return Optional.of(new ResultMessage(false,
                                                 "Category can't be deleted. At-least one transaction exists with this category."));
        }

        try {
            categoryRepository.deleteByUserIdAndCategory(String.valueOf(userId),
                                                         CryptoUtils.getEncryptedDataFromOriginal(Constants.CATEGORY_KEY,
                                                                                                  categoryName));
        } catch (CryptoException e) {
            log.error("Exception while encrypting category name.", e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<String[]> getCategories(HttpSession session) {

        Object userId = session.getAttribute("userid");

        Optional<List<Category>> categoriesOptional = categoryRepository.findAllByUserId(String.valueOf(userId));

        List<Category> categories = categoriesOptional.orElse(new ArrayList<>(0));

        return categories.stream()
                         .map(category -> categoryToCategoryCommand.convert(category))
                         .map(category -> new String[]{category.getCategory(), category.getSubcategories(), category.getDescription()})
                         .collect(Collectors.toList());
    }

    @Override
    public void addDefaultCategories(String userId, Map<String, String> categoryDescriptionMap) {

        List<Category> categoryList =
                categoryDescriptionMap.keySet()
                                      .stream()
                                      .map(category -> constructCategory(userId,
                                                                         category,
                                                                         categoryDescriptionMap.get(category)))
                                      .collect(Collectors.toList());

        categoryRepository.saveAll(categoryList);
    }

    private Category constructCategory(String userId, String name, String description) {

        Category category = new Category();

        category.setUserId(userId);

        try {
            category.setCategory(CryptoUtils.getEncryptedDataFromOriginal(Constants.CATEGORY_KEY, name));
            category.setDescription(CryptoUtils.getEncryptedDataFromOriginal(Constants.CATEGORY_KEY, description));
        } catch (CryptoException e) {
            log.error("Exception while constructing default category object.", e.getMessage());
            e.printStackTrace();
        }
        category.setDate(new Date());

        return category;
    }

    @Override
    public Optional<CategoryCommand> retrieveSpecificCategory(String categoryName, HttpSession session) {

        Object userId = session.getAttribute("userid");

        String categoryNameEncrypted = categoryName;
        try {
            categoryNameEncrypted = CryptoUtils.getEncryptedDataFromOriginal(Constants.CATEGORY_KEY, categoryName);
        } catch (CryptoException e) {
            log.error("Exception while encrypting category name.", e.getMessage());
        }

        Optional<Category> categoryFromDb = categoryRepository.findByUserIdAndCategory(String.valueOf(userId),
                                                                                       categoryNameEncrypted);

        if (categoryFromDb.isPresent()) {

            CategoryCommand categoryCommand = categoryToCategoryCommand.convert(categoryFromDb.get());

            return Optional.of(categoryCommand);
        }


        return Optional.empty();
    }
}
