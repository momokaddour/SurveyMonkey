package org.example.results;

/**
 * Result interface
 * @author Matthew Parker
 */
public interface Result {
    /**
     * Adds a response s
     *
     * @param s String
     */
    void addResponse(String s);

    /**
     * Setter for the result id
     *
     * @param id Integer
    void setId(Integer id);

    /**
     * Getter for result id
     *
     * @return
    Integer getId();*/
}
