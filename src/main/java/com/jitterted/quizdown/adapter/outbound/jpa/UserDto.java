package com.jitterted.quizdown.adapter.outbound.jpa;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Entity
class UserDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerDto> answers;

    public UserDto(Long id, String userName, List<AnswerDto> answers) {
        this.id = id;
        this.userName = userName;
        this.answers = answers;
    }

    public UserDto() {
    }

    public Long getId() {
        return this.id;
    }

    public String getUserName() {
        return this.userName;
    }

    public List<AnswerDto> getAnswers() {
        return this.answers;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAnswers(List<AnswerDto> answers) {
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserDto userDto = (UserDto) o;

        return Objects.equals(id, userDto.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
