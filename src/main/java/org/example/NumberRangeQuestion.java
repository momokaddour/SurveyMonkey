package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
public class NumberRangeQuestion implements Question{
    private String question;
    private int minNumber;
    private int maxNumber;
    @Id
    @GeneratedValue
    private Integer id;
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
    public void setId(Integer id) {

    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public String toString() {
        return question;
    }
}
