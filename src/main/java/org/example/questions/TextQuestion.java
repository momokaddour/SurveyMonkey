package org.example.questions;

import jakarta.persistence.Entity;
import org.example.questions.AbstractQuestion;
import org.example.questions.Question;

/**
 * Text Question Class, extends Abstract Question and implements Question
 * @author Kousha Motazedian
 */
@Entity
public class TextQuestion extends AbstractQuestion implements Question {
    private String question;

    /**
     * Constructor of the Class
     */
    public TextQuestion() {};

    /**
     * Constructor of the Class with parameter
     *
     * @param q
     * */
    public TextQuestion(String q) {
        question = q;
    };

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

    @Override
    public String getType() {
        return "TEXT";
    }

    @Override
    public String toString() {
        return this.question;
    }
}
