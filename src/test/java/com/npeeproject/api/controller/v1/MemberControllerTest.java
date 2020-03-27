package com.npeeproject.api.controller.v1;

import lombok.With;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    private String token;


    @Before
    public void setup() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    public void setMember() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("email", "j@m.c");
        params.add("password", "1234");
        MvcResult result = mockMvc.perform(post("/v1/signin").params(params))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").exists())
                .andReturn();

        String resultString = result.getResponse().getContentAsString();
        JacksonJsonParser jacksonJsonParser = new JacksonJsonParser();
        token = jacksonJsonParser.parseMap(resultString).get("data").toString();

    }

    @Test
    // @WithMockUser(username = "tester", roles = {"MEMBER"})
    @WithAnonymousUser
    public void invalidToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/members")
                .header("X-AUTH-TOKEN", "abcdefg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/exception/entrypoint"));
    }

    @Test
    @WithMockUser(username = "tester", roles = {"MEMBER"})
    public void accessDenied() throws Exception {
        setMember();
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/members"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/exception/accessdenied"));
    }

    @Test
    @WithMockUser(username = "tester", roles = {"ADMIN"})
    public void admin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/members"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(redirectedUrl(null));
    }

}
