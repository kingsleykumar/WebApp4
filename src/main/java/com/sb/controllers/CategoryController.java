package com.sb.controllers;

import com.sb.commands.CategoryCommand;
import com.sb.services.CategoryService;
import com.sb.services.containers.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by Kingsley Kumar on 24/03/2018 at 22:59.
 */
@Slf4j
@Controller
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category/list")
    public String getCategoriesListPage(Model model, HttpSession session) {

        log.info("---- CategoryController.getCategoriesListPage ----");

        List<String[]> categoriesList = categoryService.getCategories(session);

        Collections.sort(categoriesList, (o1, o2) -> o1[0].compareToIgnoreCase(o2[0]));

        model.addAttribute("categories", categoriesList);

        model.addAttribute("title", "Categories");

        return "view/listcategories";
    }

    @GetMapping("/category/add")
    public String getCategoryAddPage(Model model, HttpSession session) {

        log.info("---- CategoryController.getCategoryAddPage ----");

        CategoryCommand category = new CategoryCommand();

        model.addAttribute("actiontype", "Add");
        model.addAttribute("title", "Add Category");
        model.addAttribute("categorycommand", category);

        return "view/addeditcategory";
    }

    @PostMapping("/category/add")
    public String handlePostFromCategoryAddPage(
            @Valid @ModelAttribute("categorycommand") CategoryCommand categorycommand,
            BindingResult bindingResult,
            Model model, HttpSession session) {

        log.info("---- CategoryController.handlePostFromCategoryAddPage ----");

        if (bindingResult.hasErrors()) {

            model.addAttribute("title", "Add Category");
            model.addAttribute("actiontype", "Add");

            return "view/addeditcategory";
        }

        Optional<ResultMessage> resultMessageOptional = categoryService.addCategory(categorycommand, session);

        if (resultMessageOptional.isPresent()) {

            model.addAttribute("title", "Add Category");
            model.addAttribute("actiontype", "Add");

            bindingResult.rejectValue("category", "category.exists.error", resultMessageOptional.get().getMessage());

            return "view/addeditcategory";
        }

        return "redirect:/category/list";
    }

    @GetMapping("/category/{categoryName}/edit")
    public String getCategoryUpdatePage(@PathVariable String categoryName, Model model, HttpSession session) {

        log.info("---- CategoryController.getCategoryUpdatePage ----");

        Optional<CategoryCommand> categoryCommandOptional = categoryService.retrieveSpecificCategory(categoryName,
                                                                                                     session);

        if (!categoryCommandOptional.isPresent()) {

            return "redirect:/category/add";
        }

        CategoryCommand categoryCommand = categoryCommandOptional.get();

        model.addAttribute("actiontype", "Update");
        model.addAttribute("categoryName", categoryCommand.getCategory());
        model.addAttribute("title", "Edit Category");
        model.addAttribute("categorycommand", categoryCommand);

        return "view/addeditcategory";
    }

    @PostMapping("/category/{categoryName}/edit")
    public String handlePostFromCategoryUpdatePage(
            @Valid @ModelAttribute("categorycommand") CategoryCommand categorycommand,
            BindingResult bindingResult,
            Model model, HttpSession session) {

        log.info("---- CategoryController.handlePostFromCategoryUpdatePage ----");

        if (bindingResult.hasErrors()) {

            model.addAttribute("title", "Edit Category");
            model.addAttribute("actiontype", "Update");

            return "view/addeditcategory";
        }

        categoryService.updateCategory(categorycommand, session);

        return "redirect:/category/list";
    }

    @GetMapping("/category/{categoryName}/delete")
    public String deleteCategory(@PathVariable String categoryName, Model model, HttpSession session,
                                 RedirectAttributes redirectAttributes) {

        log.info("---- CategoryController.deleteCategory ----");

        Optional<ResultMessage> resultMessage = categoryService.deleteCategory(categoryName, session);

        if (resultMessage.isPresent()) {

            redirectAttributes.addFlashAttribute("message", resultMessage.get().getMessage());
        }

        return "redirect:/category/list";
    }
}
