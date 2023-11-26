package org.example.results;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Aggregate class for persisting all results
 * @author Matthew Parker
 */
@Entity
public class Aggregate {

    @OneToMany(cascade = CascadeType.ALL, targetEntity = AbstractResult.class)
    private List<Result> results;

    @Id
    @GeneratedValue
    @Column(name = "_id")
    private Integer aggregateID;

    /**
     * Constructor for Aggregate class
     */
    public Aggregate(){
        results = new ArrayList<>();
    }

    /**
     * Adds the result to the total list of results
     *
     * @param r Result
     */
    public void addResult(Result r) { results.add(r); }

    /**
     * Gets the result based on a specified index
     *
     * @param index Integer
     * @return result
     */
    public Result getResult(Integer index){ return results.get(index); }

    public List<Result> getResults()
    {
        return this.results;
    }

    /**
     * Getter for surveyID
     *
     * @return surveyID
     */
    public Integer getAggregateID() {return aggregateID;}

    /**
     * Setter for the surveyID
     *
     * @param id Integer
     */
    public void setAggregateID(Integer id){ aggregateID = id; }

}
