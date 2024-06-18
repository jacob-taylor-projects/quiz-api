package com.cooksys.quiz_api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Answer {

  @Id
  @GeneratedValue
  private Long id;

  private String text;

  private boolean correct = false;

  @ManyToOne
  @JoinColumn(name = "question_id")
  private Question question;

}
