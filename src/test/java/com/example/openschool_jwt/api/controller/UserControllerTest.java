package com.example.openschool_jwt.api.controller;

import com.example.openschool_jwt.controller.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.example.openschool_jwt.model.dto.user.UserDto;
import com.example.openschool_jwt.model.User;
import com.example.openschool_jwt.service.UserServiceImpl;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = UserController.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WithMockUser
@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование контроллера UserController")
class UserControllerTest {

    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    UserServiceImpl userService;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private SecurityContext securityContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test

    @DisplayName("Проверка метода findById при успешном выполнении")
    public void testGetUserById_Success() throws Exception {
        var responseFromService = User.builder().id(1L).firstname("user").email("admin@mail.ru").password("admin").build();
        when(userService.findById(1L)).thenReturn(responseFromService);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("admin@mail.ru")))
                .andExpect(jsonPath("$.firstname", is("user")));
        verify(userService, Mockito.times(1)).findById(1L);
    }
}