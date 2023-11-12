package org.example.repos;

import org.example.survey.Form;
import org.springframework.data.repository.CrudRepository;

public interface FormRepo extends CrudRepository<Form, Integer> {

    /**
     * CRUD Method that identifies a Form object through the assigned ID. Used for querying from DB.
     * @return none
     * @param id Integer
     * */
    Form findByFormID(Integer id);
}
