package com.jitterted.quizdown;

import com.jitterted.quizdown.domain.AnswerValidator;
import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionStore;
import com.jitterted.quizdown.domain.QuestionType;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionStoreTest {

  @Test
  public void questionIsFoundByItsNumber() throws Exception {
    QuestionStore questionStore = new QuestionStore();

    Question question = new Question(QuestionType.MC, "choose", new AnswerValidator(""), 9);
    questionStore.save(question);

    assertThat(questionStore.findByNumber(9))
        .isNotNull();
  }

  @Test
  public void noQuestionIsForUnknownNumber() throws Exception {
    QuestionStore questionStore = new QuestionStore();

    assertThat(questionStore.findByNumber(1))
        .isNull();
  }

  @Test
  public void newQuestionHasQuestionNumber() throws Exception {
    QuestionStore questionStore = new QuestionStore();

    Question question = questionStore.create(QuestionType.MC, "pick one", new AnswerValidator(""));

    assertThat(question.number())
        .isEqualTo(1);
  }
}