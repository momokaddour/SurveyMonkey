package org.example.repos;

import org.example.questions.AbstractQuestion;
import org.example.questions.Question;
import org.example.survey.Survey;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepo extends CrudRepository<AbstractQuestion, Integer> {

    /**
     * CRUD Method that identifies a Survey object through the assigned ID. Used for querying from DB.
     * @return none
     * @param id Integer
     * */
    AbstractQuestion findByQuestionId(Integer id);
}
