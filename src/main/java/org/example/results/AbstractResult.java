package org.example.results;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Abstract Result class used for persistence
 * @author Matthew Parker
 */
@Entity
public abstract class AbstractResult {

    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Method for setting the result ID
     *
     * @param id Integer
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Method for getting the ID
     *
     * @return id
     */
    public Integer getId() {
        return id;
    }

}
