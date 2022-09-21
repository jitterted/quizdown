package com.jitterted.quizdown.domain;

public class Question {
    private final QuestionType type;
    private final String content;
    private final AnswerValidator answerValidator;
    private final int number;

    public Question(QuestionType questionType, String content, AnswerValidator validator) {
        this(questionType, content, validator, -1);
    }

    public Question(QuestionType type, String content, AnswerValidator answerValidator, int number) {
        this.type = type;
        this.content = content;
        this.answerValidator = answerValidator;
        this.number = number;
    }

    public boolean isCorrectFor(Response response) {
        return answerValidator.isCorrectFor(response);
    }

    @Override
    public String toString() {
        return "{Question: #" + number +
                ", Type: " + type.toString() +
                ", Content: " + content +
                ", Answer Validator = " + answerValidator + "}";
    }

    public QuestionType type() {
        return type;
    }

    public String content() {
        return content;
    }

    public int number() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Question question = (Question) o;

        return number == question.number;
    }

    @Override
    public int hashCode() {
        return number;
    }
}
