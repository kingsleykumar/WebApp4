package com.sb.services;

import com.sb.commands.CategoryCommand;
import com.sb.services.containers.ResultMessage;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Kingsley Kumar on 24/03/2018 at 22:35.
 */
public interface CategoryService {

    Optional<ResultMessage> addCategory(CategoryCommand categoryCommand, HttpSession session);

    Optional<ResultMessage> updateCategory(CategoryCommand categoryCommand, HttpSession session);

    Optional<ResultMessage> deleteCategory(String categoryName, HttpSession session);

    List<String[]> getCategories(HttpSession session);

    void addDefaultCategories(String userId, Map<String, String> categoryDescriptionMap);

    Optional<CategoryCommand> retrieveSpecificCategory(String categoryName, HttpSession session);
}
