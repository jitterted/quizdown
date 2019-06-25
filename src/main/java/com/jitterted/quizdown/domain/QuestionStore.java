package com.jitterted.quizdown.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class QuestionStore {
  private AtomicInteger questionSequence = new AtomicInteger(1);
  private Map<Integer, Question> questions = new HashMap<>();

  public QuestionStore() {
  }

  public QuestionStore(List<Question> questionList) {
    questionList.forEach(this::save);
  }

  public Question create(QuestionType questionType, String content, AnswerValidator answerValidator) {
    Question question = new Question(questionType, content, answerValidator, questionSequence.getAndIncrement());
    save(question);
    return question;
  }

  public Question findByNumber(int number) {
    return questions.get(number);
  }

  private void save(Question question) {
    questions.put(question.number(), question);
  }

  public List<Question> questions() {
    return new ArrayList<>(questions.values());
  }

  public boolean isLastQuestion(int questionNumber) {
    return questionNumber == questions.size();
  }

}
