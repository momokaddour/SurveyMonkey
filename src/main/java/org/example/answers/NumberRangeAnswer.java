package org.example.answers;

import jakarta.persistence.Entity;
import org.example.answers.AbstractAnswer;
import org.example.answers.Answer;

/**
 * NumberRangeAnswer class for Numebr range type answers
 * @author Matthew Parker
 */
@Entity
public class NumberRangeAnswer extends AbstractAnswer implements Answer {
    private String answer;

    /**
     * Class constructor
     */
    public NumberRangeAnswer(){}

    /**
     * Class constructor for a given String s
     *
     * @param s String
     */
    public NumberRangeAnswer(String s){
        answer = s;
    }

    /**
     * Setter for answer
     *
     * @param s String
     */
    public void setAnswer(String s) {answer = s;}

    /**
     * Getter for answer
     *
     * @return answer
     */
    public String getAnswer() { return answer; }

    /**
     * Getter for answer type
     *
     * @return "NumberRangeAnswer"
     */
    public String getAnswerType(){ return "NumberRangeAnswer"; }

}