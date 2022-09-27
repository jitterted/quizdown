package com.jitterted.quizdown.domain;

public class Question {
    private final QuestionType type;
    private final String stem;
    private final AnswerValidator answerValidator;
    private final int number;

    public Question(QuestionType questionType, String stem, AnswerValidator validator) {
        this(questionType, stem, validator, -1);
    }

    public Question(QuestionType type, String stem, AnswerValidator answerValidator, int number) {
        this.type = type;
        this.stem = stem;
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
                ", Content: " + stem +
                ", Answer Validator = " + answerValidator + "}";
    }

    public QuestionType type() {
        return type;
    }

    public String stem() {
        return stem;
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
