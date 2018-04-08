package com.sb.services;

import com.sb.commands.BudgetCommand;
import com.sb.convertors.BudgetCommandToBudget;
import com.sb.convertors.BudgetToBudgetCommand;
import com.sb.domain.Budget;
import com.sb.repositories.BudgetRepository;
import com.sb.repositories.TransactionRepository;
import com.sb.services.containers.ResultMessage;
import com.sb.services.utils.CryptoException;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.sb.services.utils.CommonUtils.getDate;
import static com.sb.services.utils.Constants.BUDGET_KEY;
import static com.sb.services.utils.CryptoUtils.getEncryptedDataFromOriginal;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Slf4j
public class BudgetServiceImplTest {

    private BudgetService budgetService;

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private BudgetToBudgetCommand budgetToBudgetCommand;

    @Mock
    private BudgetCommandToBudget budgetCommandToBudget;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    MockHttpSession session;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        budgetService = new BudgetServiceImpl(budgetRepository,
                                              budgetToBudgetCommand,
                                              budgetCommandToBudget,
                                              transactionRepository);

        session.putValue("userid", "34234234");
    }

    @Test
    public void getAllBudgets() throws CryptoException {

        //given
        Budget budget = new Budget();
        budget.setName(getEncryptedDataFromOriginal(BUDGET_KEY, "March 2018"));
        budget.setAmount(getEncryptedDataFromOriginal(BUDGET_KEY, "2000"));
        budget.setFrom(getDate("01/03/2018", ofPattern("dd/MM/yyyy")));
        budget.setTo(getDate("31/03/2018", ofPattern("dd/MM/yyyy")));
        List<Budget> budgetList = new ArrayList<>(Arrays.asList(budget));

        when(budgetRepository.findAllByUserId(anyString())).thenReturn(Optional.of(budgetList));

        when(budgetToBudgetCommand.convert(any())).thenReturn(new BudgetCommand());

        //when
        List<BudgetCommand> budgetCommands = budgetService.getAllBudgets(session);

        //then
        assertTrue(budgetCommands.size() == 1);
        verify(budgetRepository, times(1)).findAllByUserId(anyString());
        verify(budgetToBudgetCommand, times(1)).convert(any());
    }

    @Test
    public void addBudget() throws CryptoException {

        //given
        Budget budget = new Budget();
        budget.setName(getEncryptedDataFromOriginal(BUDGET_KEY, "March 2018"));
        budget.setAmount(getEncryptedDataFromOriginal(BUDGET_KEY, "2000"));
        budget.setFrom(getDate("01/03/2018", ofPattern("dd/MM/yyyy")));
        budget.setTo(getDate("31/03/2018", ofPattern("dd/MM/yyyy")));
        when(budgetCommandToBudget.convert(any())).thenReturn(budget);
        when(budgetRepository.findByUserIdAndName(anyString(), anyString())).thenReturn(Optional.empty());
        when(budgetRepository.save(any())).thenReturn(budget);

        //when
        Optional<ResultMessage> result = budgetService.addBudget(any(), session);

        //then
        assertFalse(result.isPresent());
        verify(budgetCommandToBudget, times(1)).convert(any());
        verify(budgetRepository, times(1)).findByUserIdAndName(anyString(), anyString());
        verify(budgetRepository, times(1)).save(any());
    }

    @Test
    public void updateBudget() throws CryptoException {

        //given
        Budget budget = new Budget();
        budget.setName(getEncryptedDataFromOriginal(BUDGET_KEY, "March 2018"));
        budget.setAmount(getEncryptedDataFromOriginal(BUDGET_KEY, "2000"));
        budget.setFrom(getDate("01/03/2018", ofPattern("dd/MM/yyyy")));
        budget.setTo(getDate("31/03/2018", ofPattern("dd/MM/yyyy")));
        when(budgetCommandToBudget.convert(any())).thenReturn(budget);
        budget.set_id(new ObjectId());
        when(budgetRepository.findByUserIdAndName(anyString(), anyString())).thenReturn(Optional.of(budget));
        when(budgetRepository.save(any())).thenReturn(budget);

        //when
        Optional<ResultMessage> result = budgetService.updateBudget(any(), session);

        //then
        assertFalse(result.isPresent());
        verify(budgetCommandToBudget, times(1)).convert(any());
        verify(budgetRepository, times(1)).findByUserIdAndName(anyString(), anyString());
        verify(budgetRepository, never()).findAllByUserId(anyString());
        verify(budgetRepository, times(1)).save(any());
    }

    @Test
    public void deleteBudget() throws CryptoException {

        //given
        when(transactionRepository.anyTransactionExistsForBudget(anyString(), anyString())).thenReturn(false);

        //when
        Optional<ResultMessage> resultMessage = budgetService.deleteBudget("March 2018", session);

        //then
        assertFalse(resultMessage.isPresent());
        verify(transactionRepository, times(1)).anyTransactionExistsForBudget(anyString(), anyString());
        verify(budgetRepository, times(1)).deleteByUserIdAndName(anyString(), anyString());

    }

    @Test
    public void deleteBudgetFail() throws CryptoException {

        //given
        when(transactionRepository.anyTransactionExistsForBudget(anyString(), anyString())).thenReturn(true);

        //when
        Optional<ResultMessage> resultMessage = budgetService.deleteBudget("March 2018", session);

        //then
        assertTrue(resultMessage.isPresent());
        verify(transactionRepository, times(1)).anyTransactionExistsForBudget(anyString(), anyString());
        verify(budgetRepository, never()).deleteByUserIdAndName(anyString(), anyString());

    }

    @Test
    public void retrieveSpecificBudget() {

        //given
        Budget budget = new Budget();
        when(budgetRepository.findByUserIdAndName(anyString(), anyString())).thenReturn(Optional.of(budget));

        BudgetCommand budgetCommand = new BudgetCommand();
        budgetCommand.setName("March 2018");
        budgetCommand.setAmount("2000");
        when(budgetToBudgetCommand.convert(any())).thenReturn(budgetCommand);

        //when
        Optional<BudgetCommand> budgetCommandOptional = budgetService.retrieveSpecificBudget("March 2018", session);

        //then
        assertTrue(budgetCommandOptional.isPresent());
        assertEquals("March 2018", budgetCommandOptional.get().getName());
        assertEquals("2000", budgetCommandOptional.get().getAmount());
        verify(budgetRepository, times(1)).findByUserIdAndName(anyString(), anyString());
        verify(budgetToBudgetCommand, times(1)).convert(any());


    }
}