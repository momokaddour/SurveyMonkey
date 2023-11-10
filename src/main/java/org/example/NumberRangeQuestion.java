package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Number Range Question extends AbstractQuestion implements Question
 * @author Kousha Motazedian
 */
@Entity
public class NumberRangeQuestion extends AbstractQuestion implements Question{
    private String question;
    private int minNumber;
    private int maxNumber;

    public NumberRangeQuestion()
    {}

    public NumberRangeQuestion(String q, int minNumber, int maxNumber)
    {
        this.question = q;
        this.maxNumber = maxNumber;
        this.minNumber = minNumber;
    }

    /**
     * Sets the question
     * @param q String
     */
    @Override
    public void setQuestion(String q) {
        question = q;
    }

    /**
     * Gets the question
     * @return String
     */
    @Override
    public String getQuestion() {
        return question;
    }

    /**
     * Sets the Max number for the question
     * @param maxNumber, int
     */
    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    /**
     * Set the min number for the question
     * @param minNumber, int
     */
    public void setMinNumber(int minNumber) {
        this.minNumber = minNumber;
    }

    /**
     * Gets the max number
     * @return int
     */
    public int getMaxNumber() {
        return maxNumber;
    }

    /**
     * gets the min number
     * @return int
     */
    public int getMinNumber() {
        return minNumber;
    }

    /**
     * ToString Override
     * @return String
     */
    @Override
    public String toString() {
        return question;
    }
}
