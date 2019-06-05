package com.jitterted.quizdown.adapter.port.repository.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class AnswerDto {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private int questionNumber;

  @ElementCollection
  private Set<String> responses;

}
