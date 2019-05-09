package com.jitterted.quizdown;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QuizControllerIntegrationTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  public void quizIsDoneAfter2Questions() throws Exception {
    MvcResult mvcResult = mockMvc.perform(get("/"))
                                 .andReturn();

    assertThat(mvcResult.getResponse().getContentAsString())
        .contains("What is the best Java live coding stream?");

    mockMvc.perform(post("/answer"))
           .andExpect(MockMvcResultMatchers.redirectedUrl("/"));

    mvcResult = mockMvc.perform(get("/"))
                       .andReturn();

    assertThat(mvcResult.getResponse().getContentAsString())
        .contains("What is your favorite Java keyword");

    mockMvc.perform(post("/answer"))
           .andExpect(MockMvcResultMatchers.redirectedUrl("/done"));

    mvcResult = mockMvc.perform(get("/done"))
                       .andReturn();
  }

  @TestConfiguration
  static class TestConfig {
    @Bean
    @Primary
    public QuestionIterator testIterator() {
      return new QuestionIterator("{fib} What is the best Java live coding stream?\n" +
                                      "\n" +
                                      "---\n" +
                                      "\n" +
                                      "{fib} What is your favorite Java keyword?\n");
    }
  }

}