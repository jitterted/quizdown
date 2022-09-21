package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.application.QuestionStore;
import com.jitterted.quizdown.domain.DefaultAnswerValidator;
import com.jitterted.quizdown.domain.QuestionType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Tag("integration")
public class QuizControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void enterUserNameStartsQuiz() throws Exception {
        String body = webTestClient.get().uri("/").exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class)
                .returnResult().getResponseBody();
        assertThat(body)
                .contains("What is your First Name?", "<input", "type=\"text\"", "id=\"firstName\"");
    }

    @Test
    public void quizIsDoneAfter2Questions() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/question")
                                                      .sessionAttr("name", "Calvin")
                                                      .param("question", "1"))
                .andExpect(request().sessionAttribute("name", "Calvin"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString())
                .contains("What is the best live coding stream?")
                .contains("name=\"question\"")
                .contains("value=\"1\"");

        mockMvc.perform(post("/answer")
                                .sessionAttr("name", "Calvin")
                                .param("a", "on")
                                .param("question", "1"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/question?question=2"));

        mvcResult = mockMvc.perform(get("/question?question=2")
                                            .sessionAttr("name", "Calvin"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString())
                .contains("What is the best Java live coding stream?")
                .contains("name=\"question\"")
                .contains("value=\"2\"");

        mockMvc.perform(post("/answer")
                                .sessionAttr("name", "Calvin")
                                .param("question", "2"))
                .andExpect(model().attribute("question", "2"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/confirm-finished?question=2"));

        mvcResult = mockMvc.perform(get("/confirm-finished")
                                            .param("question", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("confirm-finished"))
                .andExpect(model().attribute("question", 2))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString())
                .contains("href=\"/question?question=2\"");
    }

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