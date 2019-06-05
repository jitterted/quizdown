package com.jitterted.quizdown;

import com.jitterted.quizdown.domain.DefaultAnswerValidator;
import com.jitterted.quizdown.domain.QuestionStore;
import com.jitterted.quizdown.domain.QuestionType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuizControllerIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  public void enterUserNameStartsQuiz() throws Exception {
    String body = webTestClient.get().uri("/").exchange()
                               .expectStatus().is2xxSuccessful()
                               .expectBody(String.class)
                               .returnResult().getResponseBody();
    assertThat(body)
        .contains("What is your First Name?", "<input", "type=\"text\"", "id=\"firstName\"");
  }

//  @Test
//  public void quizIsDoneAfter2Questions() throws Exception {
//    MvcResult mvcResult = mockMvc.perform(get("/"))
//                                 .andExpect(request().sessionAttribute("question", 1))
//                                 .andReturn();
//
//    assertThat(mvcResult.getResponse().getContentAsString())
//        .contains("What is the best live coding stream?")
//        .contains("name=\"question\"")
//        .contains("value=\"1\"");
//
//    mockMvc.perform(post("/answer")
//                        .param("a", "on")
//                        .param("question", "1")
//                        .sessionAttr("question", 1))
//           .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
//
//    mvcResult = mockMvc.perform(get("/"))
//                       .andExpect(request().sessionAttribute("question", 2))
//                       .andReturn();
//
//    assertThat(mvcResult.getResponse().getContentAsString())
//        .contains("What is the best Java live coding stream?")
//        .contains("name=\"question\"")
//        .contains("value=\"2\"");
//
//    mockMvc.perform(post("/answer")
//                        .param("name", "answer")
//                        .param("question", "2"))
//           .andExpect(MockMvcResultMatchers.redirectedUrl("/done"));
//
//    mvcResult = mockMvc.perform(get("/done"))
//                       .andExpect(MockMvcResultMatchers.status().isOk())
//                       .andReturn();
//  }

  @TestConfiguration
  static class TestConfig {
    @Bean
    @Primary
    public QuestionStore testIterator() {
      QuestionStore questionStore = new QuestionStore();

      questionStore.create(
          QuestionType.FIB,
          "What is the best live coding stream?\n",
          DefaultAnswerValidator.forType(QuestionType.FIB).correctChoices("luckynos7evin"));

      questionStore.create(
          QuestionType.FIB,
          "What is the best Java live coding stream?\n",
          DefaultAnswerValidator.forType(QuestionType.FIB).correctChoices("jitterted"));

      return questionStore;
    }
  }

}