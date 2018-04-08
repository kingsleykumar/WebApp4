package com.sb.convertors;

import com.sb.commands.BudgetCommand;
import com.sb.domain.Budget;
import com.sb.domain.CategoryBudget;
import com.sb.services.utils.CryptoException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

import static com.sb.services.utils.CommonUtils.getDate;
import static com.sb.services.utils.Constants.BUDGET_KEY;
import static com.sb.services.utils.Constants.TX_TYPE_EXPENSE;
import static com.sb.services.utils.CryptoUtils.getEncryptedDataFromOriginal;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;

public class BudgetToBudgetCommandTest {

    private ServletRequestAttributes attrs;

    @Mock
    private MockHttpSession session;

    @Mock
    private MockHttpServletRequest servletRequest;

    private BudgetToBudgetCommand converter;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        attrs = new ServletRequestAttributes(servletRequest);

        RequestContextHolder.setRequestAttributes(attrs);

        converter = new BudgetToBudgetCommand();
    }

    @Test
    public void convert() throws CryptoException {

        //given
        when(attrs.getRequest().getSession(anyBoolean())).thenReturn(session);
        when(session.getAttribute("dateFormatJava")).thenReturn("dd/MM/yyyy");

        Budget budget = new Budget();
        budget.setName(getEncryptedDataFromOriginal(BUDGET_KEY, "March 2018"));
        budget.setAmount(getEncryptedDataFromOriginal(BUDGET_KEY, "2000"));
        budget.setFrom(getDate("01/03/2018", ofPattern("dd/MM/yyyy")));
        budget.setTo(getDate("31/03/2018", ofPattern("dd/MM/yyyy")));
        CategoryBudget categoryBudget = new CategoryBudget();
        categoryBudget.setType(TX_TYPE_EXPENSE);
        categoryBudget.setAmount(getEncryptedDataFromOriginal(BUDGET_KEY, "250"));
        categoryBudget.setName(getEncryptedDataFromOriginal(BUDGET_KEY, "Food"));
        budget.setCategorybudgets(Arrays.asList(categoryBudget));

        //when
        BudgetCommand budgetCommand = converter.convert(budget);

        //then
        assertEquals("March 2018", budgetCommand.getName());
        assertEquals("2000", budgetCommand.getAmount());
        assertEquals("01/03/2018", budgetCommand.getFrom());
        assertEquals("31/03/2018", budgetCommand.getTo());
        assertEquals(1, budgetCommand.getCategoryBudgets().size());
        assertEquals("Food", budgetCommand.getCategoryBudgets().get(0).getName());
        assertEquals("250", budgetCommand.getCategoryBudgets().get(0).getAmount());
    }
}