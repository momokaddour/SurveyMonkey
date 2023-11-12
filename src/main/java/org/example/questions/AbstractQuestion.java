package org.example.questions;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Abstract Class for Question, used for persistence
 * @author Kousha Motazedian
 */
@Entity
public abstract class AbstractQuestion {
    @Id
    @GeneratedValue
    private Integer questionId;

    /**
     * Set the ID for the question
     * @param id Integer
     */
    public void setId(Integer id) {
        this.questionId = id;
    }

    /**
     * Get the ID for the question
     * @return Integer
     */
    public Integer getId() {
        return questionId;
    }
}
