package org.example.answers;

import jakarta.persistence.Entity;
import org.example.answers.AbstractAnswer;
import org.example.answers.Answer;

import javax.jdo.annotations.PersistenceCapable;

/**
 * TextAnswer class for Text answer type answers
 * @author Matthew Parker
 */
@Entity
@PersistenceCapable
public class TextAnswer extends AbstractAnswer implements Answer {
    private String answer;

    /**
     * Class constructor
     */
    public TextAnswer(){}

    /**
     * Class constructor for a String s
     *
     * @param s String
     */
    public TextAnswer(String s){
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
     * @return "TextAnswer"
     */
    public String getAnswerType(){ return "TextAnswer"; }

}
