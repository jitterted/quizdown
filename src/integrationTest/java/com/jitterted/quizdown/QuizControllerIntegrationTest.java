package com.jitterted.quizdown;

import com.jitterted.quizdown.domain.AnswerValidator;
import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionStore;
import com.jitterted.quizdown.domain.QuestionType;
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
        .contains("What is the best live coding stream?")
        .contains("name=\"question\"")
        .contains("value=\"1\"");

    mockMvc.perform(post("/answer")
                        .param("a", "on")
                        .param("question", "1"))
           .andExpect(MockMvcResultMatchers.redirectedUrl("/"));

    mvcResult = mockMvc.perform(get("/"))
                       .andReturn();

    assertThat(mvcResult.getResponse().getContentAsString())
        .contains("What is the best Java live coding stream?")
        .contains("name=\"question\"")
        .contains("value=\"2\"");

    mockMvc.perform(post("/answer")
                        .param("name", "answer")
                        .param("question", "2"))
           .andExpect(MockMvcResultMatchers.redirectedUrl("/done"));

    mvcResult = mockMvc.perform(get("/done"))
                       .andExpect(MockMvcResultMatchers.status().isOk())
                       .andReturn();
  }

  @TestConfiguration
  static class TestConfig {
    @Bean
    @Primary
    public QuestionStore testIterator() {
      QuestionStore questionStore = new QuestionStore();

      Question question1 = questionStore.create(
          QuestionType.FIB,
          "What is the best live coding stream?\n",
          new AnswerValidator("luckynos7evin"));
      questionStore.save(question1);

      Question question2 = questionStore.create(
          QuestionType.FIB,
          "What is the best Java live coding stream?\n",
          new AnswerValidator("jitterted"));
      questionStore.save(question2);

      return questionStore;
    }
  }

}