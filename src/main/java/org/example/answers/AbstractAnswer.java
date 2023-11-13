package org.example.answers;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Abstract answer class for persisting answers
 * @author Matthew Parker
 */
@Entity
public class AbstractAnswer {
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Setter for id
     */
    public void setId() {this.id = id;}

    /**
     * Getter for id
     *
     * @return id
     */
    public Integer getId() { return id; }
}
