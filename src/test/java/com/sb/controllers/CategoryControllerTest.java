package com.sb.controllers;

import com.sb.commands.CategoryCommand;
import com.sb.services.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    private CategoryController controller;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new CategoryController(categoryService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getCategoriesListPage() throws Exception {
        //given
        List<String[]> categoriesList = new ArrayList<>(0);
        when(categoryService.getCategories(any())).thenReturn(categoriesList);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/category/list"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("view/listcategories"))
               .andExpect(MockMvcResultMatchers.model().attributeExists("categories"))
               .andExpect(MockMvcResultMatchers.model().attributeExists("title"));

        //then
        verify(categoryService, times(1)).getCategories(any());
    }

    @Test
    public void getCategoryAddPage() throws Exception {

//        CategoryCommand categoryCommand = new CategoryCommand();

        mockMvc.perform(get("/category/add"))
               .andExpect(status().isOk())
               .andExpect(view().name("view/addeditcategory"))
               .andExpect(model().attributeExists("actiontype"))
               .andExpect(model().attribute("actiontype", "Add"))
               .andExpect(model().attributeExists("title"))
               .andExpect(model().attributeExists("categorycommand"));
    }

    @Test
    public void handlePostFromCategoryAddPage() throws Exception {

        //given
        when(categoryService.addCategory(any(), any())).thenReturn(Optional.empty());

        //when
        mockMvc.perform(post("/category/add").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                             .param("category", "Food")
                                             .param("description", "Food related expenses")
                                             .param("subcategories", "Food, Lunch"))
               .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
               .andExpect(MockMvcResultMatchers.view().name("redirect:/category/list"));

        //then
        verify(categoryService, times(1)).addCategory(any(), any());
    }

    @Test
    public void handlePostFromCategoryAddPageValidationFail() throws Exception {

        mockMvc.perform(post("/category/add").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                             .param("category",
                                                    "One Very Large Category Name which is Over Fifty characters in Length.")
                                             .param("description", "Food related expenses")
                                             .param("subcategories", "Food, Lunch"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("view/addeditcategory"));
    }

    @Test
    public void getCategoryUpdatePage() throws Exception {

        //given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setCategory("Food");
        when(categoryService.retrieveSpecificCategory(anyString(), any()))
                .thenReturn(Optional.of(categoryCommand));

        //when
        mockMvc.perform(get("/category/Food/edit"))
               .andExpect(status().isOk())
               .andExpect(model().attributeExists("actiontype"))
               .andExpect(model().attributeExists("categoryName"))
               .andExpect(model().attribute("categoryName", "Food"))
               .andExpect(model().attributeExists("title"))
               .andExpect(model().attributeExists("categorycommand"))
               .andExpect(view().name("view/addeditcategory"));

        //then
        verify(categoryService, times(1)).retrieveSpecificCategory(anyString(), any());
    }

    @Test
    public void getCategoryUpdatePageNotFound() throws Exception {

        //given
        when(categoryService.retrieveSpecificCategory(anyString(), any()))
                .thenReturn(Optional.empty());

        //when
        mockMvc.perform(get("/category/Food/edit"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/category/add"));

        //then
        verify(categoryService, times(1)).retrieveSpecificCategory(anyString(), any());
    }


    @Test
    public void handlePostFromCategoryUpdatePage() throws Exception {

        //then
        mockMvc.perform(post("/category/Food/edit")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("category", "Food")
                                .param("description", "Food related expenses")
                                .param("subcategories", "Food, Lunch"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/category/list"));

        verify(categoryService, times(1)).updateCategory(any(), any());
    }

    @Test
    public void deleteCategory() throws Exception {

        //then
        mockMvc.perform(get("/category/Food/delete"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/category/list"));


        verify(categoryService, times(1)).deleteCategory(any(), any());
    }
}