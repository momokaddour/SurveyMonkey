package org.example;

import jakarta.persistence.Entity;

@Entity
public class NumberRangeAnswer extends AbstractAnswer implements Answer{
    private String answer;

    public NumberRangeAnswer(){}

    public void setAnswer(String s) {answer = s;}

    public String getAnswer() { return answer; }

    public String getAnswerType(){ return "NumberRangeAnswer"; }

}