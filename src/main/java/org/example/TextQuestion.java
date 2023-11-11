package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Text Question Class, extends Abstract Question and implements Question
 * @author Kousha Motazedian
 */
@Entity
public class TextQuestion extends AbstractQuestion implements Question{
    private String question;

    /**
     * Constructor of the Class
     */
    public TextQuestion() {};

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
     * @return
     */
    @Override
    public String getQuestion() {
        return question;
    }
    
}
