package org.example.repos;

import org.example.Survey;
import org.example.SurveyManager;
import org.springframework.data.repository.CrudRepository;

public interface SurveyRepo extends CrudRepository<Survey, Integer> {

    /**
     * CRUD Method that identifies a Survey object through the assigned ID. Used for querying from DB.
     * @return none
     * @param id Integer
     * */
    Survey findBySurveyID(Integer id);
}
