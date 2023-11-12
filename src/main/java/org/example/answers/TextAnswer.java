package org.example.answers;

import jakarta.persistence.Entity;
import org.example.answers.AbstractAnswer;
import org.example.answers.Answer;

@Entity
public class TextAnswer extends AbstractAnswer implements Answer {
    private String answer;

    public TextAnswer(){}

    public TextAnswer(String s){
        answer = s;
    }

    public void setAnswer(String s) {answer = s;}

    public String getAnswer() { return answer; }

    public String getAnswerType(){ return "TextAnswer"; }

}
