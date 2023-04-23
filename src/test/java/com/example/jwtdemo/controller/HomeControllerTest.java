package com.example.jwtdemo.controller;

import com.example.jwtdemo.service.CustomUserDetailsService;
import com.example.jwtdemo.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(controllers = HomeController.class)
@SpringBootTest
//@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    private WebApplicationContext context;


    //    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtUtil jwtUtill;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void sayHello() throws Exception {
        mockMvc.perform(get("/hello")
                        .contentType("application/json")
                        .with(user("admin").password("pass").roles("USER", "ADMIN")))
                .andExpect(status().isOk());

    }

    @Test
    void testPost() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(HomeController.class)
                .build();

        MvcResult mvcResult = mockMvc.perform(post("/unauthenticated/test-post"))
                .andExpect(status().isOk())
                .andExpect(content().string("Post here!"))
                .andReturn();

//        String responseBody = mvcResult.getResponse().getContentAsString();
//
//        assertThat(responseBody).isEqualTo("Post here!");
    }
}