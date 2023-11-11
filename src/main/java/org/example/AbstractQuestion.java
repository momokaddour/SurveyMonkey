package org.example;

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
    private Integer id;

    /**
     * Set the ID for the question
     * @param id Integer
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get the ID for the question
     * @return Integer
     */
    public Integer getId() {
        return id;
    }
}
