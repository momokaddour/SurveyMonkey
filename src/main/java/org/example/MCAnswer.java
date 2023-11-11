package org.example;

import jakarta.persistence.Entity;

@Entity
public class MCAnswer extends AbstractAnswer implements Answer{
    private String answer;

    public MCAnswer(){}

    public void setAnswer(String s) {answer = s;}

    public String getAnswer() { return answer; }

    public String getAnswerType(){ return "MCAnswer"; }

}
