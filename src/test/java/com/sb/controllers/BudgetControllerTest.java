package com.sb.controllers;

import com.sb.commands.BudgetCommand;
import com.sb.services.BudgetService;
import com.sb.services.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BudgetControllerTest {

    private BudgetController budgetController;

    @Mock
    private BudgetService budgetService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private MockHttpSession session;

    private HashMap<String, Object> sessionMap;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        budgetController = new BudgetController(budgetService, categoryService);

        mockMvc = MockMvcBuilders.standaloneSetup(budgetController).build();

        sessionMap = new HashMap<>();
        sessionMap.put("dateFormatJava", "dd/MM/yyyy");
    }

    @Test
    public void getBudgetsListPage() throws Exception {
        //given
        when(budgetService.getAllBudgets(any())).thenReturn(new ArrayList<>(0));

        //when
        mockMvc.perform(get("/budget/list").sessionAttrs(sessionMap))
               .andExpect(status().isOk())
               .andExpect(view().name("view/listbudgets"))
               .andExpect(model().attributeExists("budgets"))
               .andExpect(model().attributeExists("title"))
               .andExpect(model().attribute("title", "Budgets"));

        //then
        verify(budgetService, times(1)).getAllBudgets(any());
    }

    @Test
    public void getBudgetAddPage() throws Exception {

        //given
        when(categoryService.getCategories(any())).thenReturn(new ArrayList<>(0));

        //when
        mockMvc.perform(get("/budget/add").sessionAttrs(sessionMap))
               .andExpect(status().isOk())
               .andExpect(view().name("view/addeditbudget"))
               .andExpect(model().attributeExists("transactionTypes"))
               .andExpect(model().attributeExists("categories"))
               .andExpect(model().attributeExists("actiontype"))
               .andExpect(model().attributeExists("dateformat"))
               .andExpect(model().attributeExists("budgetcommand"))
               .andExpect(model().attributeExists("title"));

        //verify
        verify(categoryService, times(1)).getCategories(any());
    }

    @Test
    public void handlePostFromBudgetAddPage() throws Exception {

        //given
        when(budgetService.addBudget(any(), any())).thenReturn(Optional.empty());

        //when
        mockMvc.perform(post("/budget/add"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/budget/list"));

        //then
        verify(budgetService, times(1)).addBudget(any(), any());
    }

    @Test
    public void getBudgetUpdatePage() throws Exception {

        //given
        when(categoryService.getCategories(any())).thenReturn(new ArrayList<>(0));

        BudgetCommand budgetCommand = new BudgetCommand();
        budgetCommand.setName("Food");

        when(budgetService.retrieveSpecificBudget(anyString(), any())).thenReturn(Optional.of(budgetCommand));

        //when
        mockMvc.perform(get("/budget/Food/edit").sessionAttrs(sessionMap))
               .andExpect(status().isOk())
               .andExpect(view().name("view/addeditbudget"))
               .andExpect(model().attributeExists("transactionTypes"))
               .andExpect(model().attributeExists("categories"))
               .andExpect(model().attributeExists("actiontype"))
               .andExpect(model().attributeExists("dateformat"))
               .andExpect(model().attributeExists("budgetName"))
               .andExpect(model().attributeExists("budgetcommand"))
               .andExpect(model().attributeExists("title"));

        //then
        verify(budgetService, times(1)).retrieveSpecificBudget(anyString(), any());
        verify(categoryService, times(1)).getCategories(any());
    }

    @Test
    public void handlePostFromBudgetUpdatePage() {
    }

    @Test
    public void deleteBudget() {
    }
}