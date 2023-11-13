package org.example.answers;

import jakarta.persistence.Entity;
import org.example.answers.AbstractAnswer;
import org.example.answers.Answer;

/**
 * MCAnswer class for multiple choice type answers
 * @author Matthew Parker
 */
@Entity
public class MCAnswer extends AbstractAnswer implements Answer {
    private String answer;

    /**
     * Class constructor
     */
    public MCAnswer(){}

    /**
     * Class constructor using a string
     *
     * @param s String
     */
    public MCAnswer(String s){
        answer = s;
    }

    /**
     * Setter for answer with a string
     *
     * @param s String
     */
    public void setAnswer(String s) {answer = s;}

    /**
     * Getter for the answer
     *
     * @return answer
     */
    public String getAnswer() { return answer; }

    /**
     * Getter for answer type
     *
     * @return "MCAnswer"
     */
    public String getAnswerType(){ return "MCAnswer"; }

}
