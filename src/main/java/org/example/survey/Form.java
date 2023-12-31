package org.example.survey;

import jakarta.persistence.*;
import org.example.answers.AbstractAnswer;
import org.example.answers.Answer;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Form {

    @OneToMany(cascade = CascadeType.ALL, targetEntity = AbstractAnswer.class)
    private Map<Integer, Answer> answers;

    @Id
    @GeneratedValue
    @Column(name = "_id")
    private Integer formID;

    public Form(){
        answers = new HashMap<>();
    }

    public void addAnswer(Answer a, int qNum) {
        answers.put(qNum, a);
    }

    public Map<Integer, Answer> getAnswers() { return answers; }

    public Integer getFormID() {
        return formID;
    }
}
