package org.example;

import jakarta.persistence.Entity;

/**
 * Question Interface
 * @author Kousha Motazedian
 */
public interface Question {
    /**
     * Set the question
     * @param q String
     */
    void setQuestion(String q);

    /**
     * Get the question
     * @return String
     */
    String getQuestion();

    /**
     * Set the question ID
     * @param id Integer
     */
    void setId(Integer id);

    /**
     * Ger the question ID
     * @return Integer
     */
    Integer getId();
}
