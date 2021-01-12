package com.lambdaschool.todos.controllers;

import com.lambdaschool.todos.TodosApplication;
import com.lambdaschool.todos.models.Todos;
import com.lambdaschool.todos.models.User;
import com.lambdaschool.todos.repository.UserRepository;
import com.lambdaschool.todos.services.TodosService;
import com.lambdaschool.todos.services.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hibernate.Hibernate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TodosApplication.class)
@AutoConfigureMockMvc
public class TodosControllerIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    TodosService todosService;

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @Transactional
    public void completeTodo() throws Exception{
        User newUser = new User("testUser", "testpassword", "test@email.com");
        newUser.getTodos().add(new Todos(newUser, "testTodo"));
        newUser = userService.save(newUser);

        mockMvc.perform(MockMvcRequestBuilders.patch("/todos/todo/{todoid}", newUser.getTodos().get(0).getTodoid()))
            .andExpect(status().isOk());

        newUser = userService.findUserById(newUser.getUserid());
        assertTrue(newUser.getTodos().get(0).isCompleted());
    }
}