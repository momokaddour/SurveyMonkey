package org.example.questions;

import jakarta.persistence.*;

import javax.jdo.annotations.PersistenceCapable;
import java.io.Serializable;

/**
 * Abstract Class for Question, used for persistence
 * @author Kousha Motazedian
 */
@Entity
@PersistenceCapable
public abstract class AbstractQuestion implements Serializable {
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
