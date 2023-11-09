package org.example;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Form {

    @OneToMany(cascade = CascadeType.ALL, targetEntity = AbstractAnswer.class)
    private List<Answer> answers;

    @Id
    @GeneratedValue
    @Column(name = "_id")
    private Integer surveyId;

    public Form(){
        answers = new ArrayList<>();
    }

    public void addAnswer(Answer a) { answers.add(a); }

    public List<Answer> getAnswers() { return answers; }



}
