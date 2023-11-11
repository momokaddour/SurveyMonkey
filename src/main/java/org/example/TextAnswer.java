package org.example;

import jakarta.persistence.Entity;

@Entity
public class TextAnswer extends AbstractAnswer implements Answer{
    private String answer;

    public TextAnswer(){}

    public void setAnswer(String s) {answer = s;}

    public String getAnswer() { return answer; }

    public String getAnswerType(){ return "TextAnswer"; }

}
