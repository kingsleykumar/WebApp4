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
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
public class CategoryServiceImplTest {

    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryCommandToCategory categoryCommandToCategory;

    @Mock
    private TransactionRepository transactionRepository;

    private CategoryToCategoryCommand categoryToCategoryCommand;

    @Mock
    MockHttpSession session;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        categoryToCategoryCommand = new CategoryToCategoryCommand();
//        categoryCommandToCategory = new CategoryCommandToCategory();
        categoryService = new CategoryServiceImpl(categoryRepository,
                                                  categoryCommandToCategory,
                                                  categoryToCategoryCommand,
                                                  transactionRepository);
        session.putValue("userid", "34234234");
    }

    @Test
    public void addCategory() throws CryptoException {

        Category category = new Category();
        category.setCategory(CryptoUtils.getEncryptedDataFromOriginal(Constants.CATEGORY_KEY, "Food"));
        when(categoryCommandToCategory.convert(any())).thenReturn(category);

        when(categoryRepository.findByUserIdAndCategory(anyString(), anyString())).thenReturn(Optional.empty());

        when(categoryRepository.save(any())).thenReturn(category);

        //when
        Optional<ResultMessage> result = categoryService.addCategory(any(), session);

        //then
        assertEquals(Optional.empty(), result);
        verify(categoryRepository, times(1)).findByUserIdAndCategory(anyString(), anyString());
        verify(categoryRepository, times(1)).save(any());
        verify(categoryRepository, never()).findAllByUserId(any());
    }

    @Test
    public void updateCategory() throws CryptoException {

        Category category = new Category();
        category.setCategory(CryptoUtils.getEncryptedDataFromOriginal(Constants.CATEGORY_KEY, "Food"));
        when(categoryCommandToCategory.convert(any())).thenReturn(category);

        when(categoryRepository.findByUserIdAndCategory(anyString(), anyString())).thenReturn(Optional.of(category));

        when(categoryRepository.save(any())).thenReturn(category);

        //when
        Optional<ResultMessage> result = categoryService.updateCategory(any(), session);

        //then
        assertEquals(Optional.empty(), result);
        verify(categoryRepository, times(1)).findByUserIdAndCategory(anyString(), anyString());
        verify(categoryRepository, times(1)).save(any());
        verify(categoryRepository, never()).findAllByUserId(any());
    }

    @Test
    public void deleteCategory() {

        //given
        when(transactionRepository.anyTransactionExistsForCategory(anyString(), anyString())).thenReturn(false);

        //when
        Optional<ResultMessage> result = categoryService.deleteCategory(anyString(), session);

//        log.info("result.get() = " + result.get());

        //then
        assertEquals(Optional.empty(), result);
        verify(transactionRepository, times(1)).anyTransactionExistsForCategory(anyString(), anyString());
        verify(categoryRepository, times(1)).deleteByUserIdAndCategory(anyString(), anyString());
    }

    @Test
    public void deleteCategoryTxExists() {

        //given
        when(transactionRepository.anyTransactionExistsForCategory(anyString(), anyString())).thenReturn(true);

        //when
        Optional<ResultMessage> result = categoryService.deleteCategory(anyString(), session);

//        log.info("result.get() = " + result.get());

        //then
        assertTrue(result.isPresent());
        verify(transactionRepository, times(1)).anyTransactionExistsForCategory(anyString(), anyString());
        verify(categoryRepository, never()).deleteByUserIdAndCategory(anyString(), anyString());
    }

    @Test
    public void getCategories() throws CryptoException {
        //given
        Category category = new Category();
        category.setCategory(CryptoUtils.getEncryptedDataFromOriginal(Constants.CATEGORY_KEY, "Food"));
        category.setSubcategories(new ArrayList<>(0));
        category.setDescription(CryptoUtils.getEncryptedDataFromOriginal(Constants.CATEGORY_KEY, "Food"));

        when(categoryRepository.findAllByUserId(any())).thenReturn(Optional.of(Arrays.asList(category)));

//        when(categoryToCategoryCommand.convert(any())).thenReturn(new CategoryCommand());

        //when
        List<String[]> resultList = categoryService.getCategories(session);

        //then
        assertTrue(resultList.size() == 1);
        verify(categoryRepository, times(1)).findAllByUserId(any());
//        verify(categoryToCategoryCommand, times(1)).convert(any());
    }

    @Test
    public void addDefaultCategories() {

        //when
        categoryService.addDefaultCategories(anyString(), new HashMap<String, String>(0));

        //then
        verify(categoryRepository, times(1)).saveAll(anyList());
    }

    @Test
    public void retrieveSpecificCategory() throws CryptoException {

        //given
        Category category = new Category();
        category.setCategory(CryptoUtils.getEncryptedDataFromOriginal(Constants.CATEGORY_KEY, "Food"));

        when(categoryRepository.findByUserIdAndCategory(anyString(), anyString())).thenReturn(Optional.of(category));

        //when
        Optional<CategoryCommand> result = categoryService.retrieveSpecificCategory("Food", session);

        //then
        assertTrue(result.isPresent());
        assertEquals("Food", result.get().getCategory());
        verify(categoryRepository, times(1)).findByUserIdAndCategory(anyString(), anyString());
//        verify(categoryToCategoryCommand, times(1)).convert(category);
    }
}