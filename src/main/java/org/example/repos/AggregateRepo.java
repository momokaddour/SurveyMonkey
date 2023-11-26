package org.example.repos;

import org.example.results.Aggregate;

import org.springframework.data.repository.CrudRepository;

public interface AggregateRepo extends CrudRepository<Aggregate, Integer> {

    /**
     * CRUD Method that identifies an Aggregate object through the assigned ID. Used for querying from DB.
     *
     * @param id Integer
     * @return none
     */
    Aggregate findByAggregateID(Integer id);
}