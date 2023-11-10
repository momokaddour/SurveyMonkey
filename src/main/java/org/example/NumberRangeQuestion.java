package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


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

    @Override
    public void setQuestion(String q) {
        question = q;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public void setMinNumber(int minNumber) {
        this.minNumber = minNumber;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public int getMinNumber() {
        return minNumber;
    }

    @Override
    public String toString() {
        return question;
    }
}
