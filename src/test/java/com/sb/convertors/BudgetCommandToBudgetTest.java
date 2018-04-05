package com.sb.convertors;

import com.sb.commands.BudgetCommand;
import com.sb.commands.CategoryBudgetCommand;
import com.sb.domain.Budget;
import com.sb.services.utils.CryptoException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Date;

import static com.sb.services.utils.CommonUtils.getDate;
import static com.sb.services.utils.Constants.BUDGET_KEY;
import static com.sb.services.utils.Constants.TX_TYPE_EXPENSE;
import static com.sb.services.utils.CryptoUtils.getEncryptedDataFromOriginal;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;

public class BudgetCommandToBudgetTest {

    private ServletRequestAttributes attrs;

    @Mock
    private MockHttpSession session;

    @Mock
    private MockHttpServletRequest servletRequest;

    private BudgetCommandToBudget converter;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

//        servletRequest.setSession(session);

        attrs = new ServletRequestAttributes(servletRequest);

        RequestContextHolder.setRequestAttributes(attrs);

        converter = new BudgetCommandToBudget();
    }

    @Test
    public void convert() throws CryptoException {

        //given

        when(attrs.getRequest().getSession(anyBoolean())).thenReturn(session);
        when(session.getAttribute("dateFormatJava")).thenReturn("dd/MM/yyyy");

        BudgetCommand budgetCommand = new BudgetCommand();
        budgetCommand.setName("March 2018");
        budgetCommand.setAmount("2000");
        budgetCommand.setFrom("01/03/2018");
        budgetCommand.setTo("31/03/2018");
        CategoryBudgetCommand categoryBudgetCommand = new CategoryBudgetCommand();
        categoryBudgetCommand.setName("Food");
        categoryBudgetCommand.setAmount("250");
        categoryBudgetCommand.setType(TX_TYPE_EXPENSE);
        budgetCommand.setCategoryBudgets(Arrays.asList(categoryBudgetCommand));

        Date fromDate = getDate("01/03/2018", ofPattern("dd/MM/yyyy"));
        Date toDate = getDate("31/03/2018", ofPattern("dd/MM/yyyy"));

        //when
        Budget budget = converter.convert(budgetCommand);

        //then
        Assert.assertNotNull(budget);
        Assert.assertEquals(getEncryptedDataFromOriginal(BUDGET_KEY, budgetCommand.getName()), budget.getName());
        Assert.assertEquals(getEncryptedDataFromOriginal(BUDGET_KEY, budgetCommand.getAmount()), budget.getAmount());
        Assert.assertEquals(fromDate, budget.getFrom());
        Assert.assertEquals(toDate, budget.getTo());
        Assert.assertEquals(1, budget.getCategorybudgets().size());
        Assert.assertEquals(getEncryptedDataFromOriginal(BUDGET_KEY, categoryBudgetCommand.getName()),
                            budget.getCategorybudgets().get(0).getName());
    }
}