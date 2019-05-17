package com.jitterted.quizdown.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionStoreTest {

  @Test
  public void noQuestionIsFoundForUnknownNumber() throws Exception {
    QuestionStore questionStore = new QuestionStore();

    assertThat(questionStore.findByNumber(1))
        .isNull();
  }

  @Test
  public void newQuestionHasQuestionNumberOfOne() throws Exception {
    QuestionStore questionStore = new QuestionStore();

    Question question = questionStore.create(QuestionType.MC, "pick one", new DummyAnswerValidator());

    assertThat(question.number())
        .isEqualTo(1);
  }

  @Test
  public void createdQuestionIsSavedInStore() throws Exception {
    QuestionStore questionStore = new QuestionStore();

    Question question = questionStore.create(QuestionType.MC, "pick one", new DummyAnswerValidator());

    assertThat(questionStore.findByNumber(question.number()))
        .isEqualTo(question);
  }

  @Test
  public void newStoreHasCountOfZero() throws Exception {
    QuestionStore questionStore = new QuestionStore();
    assertThat(questionStore.count())
        .isZero();
  }

  @Test
  public void storeWithThreeQuestionsHasCountOfThree() throws Exception {
    QuestionStore questionStore = new QuestionStore();

    questionStore.create(QuestionType.MC, "pick one", new DummyAnswerValidator());
    questionStore.create(QuestionType.MC, "pick one", new DummyAnswerValidator());
    questionStore.create(QuestionType.MC, "pick one", new DummyAnswerValidator());

    assertThat(questionStore.count())
        .isEqualTo(3);
  }
}