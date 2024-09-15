package com.example.spring_secure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GreetingControllerCombinedTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void test() throws Exception {
        mvc.perform(post("/registerUser").with(httpBasic("Martin","Partin"))
                        .param("email", "testuser@example.com")
                        .param("password", "password"))

                .andExpect(status().isOk())
                .andExpect(view().name("registerOK"));


    }
    @Test
    public void testHTTPBasic() throws Exception{
        mvc.perform(get("/").with(httpBasic("Martin","Partin")))
                .andExpect(status().isOk());

        mvc.perform(get("/").with(httpBasic("fejkkonto","fejklösen")))
                .andExpect(status().isUnauthorized());
    }

}