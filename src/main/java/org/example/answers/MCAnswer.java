package org.example.answers;

import jakarta.persistence.Entity;
import org.example.answers.AbstractAnswer;
import org.example.answers.Answer;

@Entity
public class MCAnswer extends AbstractAnswer implements Answer {
    private String answer;

    public MCAnswer(){}

    public MCAnswer(String s){
        answer = s;
    }

    public void setAnswer(String s) {answer = s;}

    public String getAnswer() { return answer; }

    public String getAnswerType(){ return "MCAnswer"; }

}
