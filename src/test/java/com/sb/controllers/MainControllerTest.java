package com.sb.controllers;

import com.sb.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Slf4j
public class MainControllerTest {

    @Mock
    UserService userService;

    private MainController mainController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        mainController = new MainController(userService);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(mainController)
                                 .setViewResolvers(viewResolver)
                                 .build();
    }

    @Test
    public void testGetMainPage() throws Exception {

        //when
        mockMvc.perform(get("/main"))
               .andExpect(status().isOk())
               .andExpect(view().name("view/main"));
//               .andExpect(model().attributeExists("message"));
    }

    @Test
    public void testGetIndexPage() {



    }
}