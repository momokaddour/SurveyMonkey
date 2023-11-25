package org.example.answers;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import javax.jdo.annotations.PersistenceCapable;
import java.io.Serializable;

/**
 * Abstract answer class for persisting answers
 * @author Matthew Parker
 */
@Entity
@PersistenceCapable
public class AbstractAnswer implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Setter for id
     */
    public void setId(Integer id) {this.id = id;}

    /**
     * Getter for id
     *
     * @return id
     */
    public Integer getId() { return id; }
}
