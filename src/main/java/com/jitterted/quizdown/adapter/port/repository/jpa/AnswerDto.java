package com.jitterted.quizdown.adapter.port.repository.jpa;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;
import java.util.Set;

@Entity
public class AnswerDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int questionNumber;

    @ElementCollection
    private Set<String> responses;

    public AnswerDto(Long id, int questionNumber, Set<String> responses) {
        this.id = id;
        this.questionNumber = questionNumber;
        this.responses = responses;
    }

    public AnswerDto() {
    }

    public Long getId() {
        return this.id;
    }

    public int getQuestionNumber() {
        return this.questionNumber;
    }

    public Set<String> getResponses() {
        return this.responses;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public void setResponses(Set<String> responses) {
        this.responses = responses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnswerDto answerDto = (AnswerDto) o;

        return Objects.equals(id, answerDto.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AnswerDto{" +
                "id=" + id +
                ", questionNumber=" + questionNumber +
                ", responses=" + responses +
                '}';
    }
}
