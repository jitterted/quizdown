package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.Answer;
import com.jitterted.quizdown.domain.AnswerValidator;
import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionStore;
import com.jitterted.quizdown.domain.QuestionType;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerServiceTest {

  @Test
  public void convertsFormMapToMultipleChoiceAnswer() throws Exception {
    QuestionStore questionStore = new QuestionStore();
    Question question1 = new Question(QuestionType.MC, "choose", new AnswerValidator(""), 1);
    questionStore.save(question1);

    Map<String, String> map = Map.of("a", "on", "question", "1");

    AnswerService answerService = new AnswerService(questionStore);

    answerService.process(map);

    Set<Answer> answers = answerService.answers();
    assertThat(answers)
        .hasSize(1);

    Answer expectedAnswer = new Answer(question1, "a");
    assertThat(answers.iterator().next())
        .isEqualTo(expectedAnswer);
  }

}