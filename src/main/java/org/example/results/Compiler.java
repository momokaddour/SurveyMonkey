package org.example.results;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.example.answers.Answer;
import org.example.answers.ListOfAnswerResult;
import org.example.survey.Form;
import org.example.survey.Survey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Responsible for compiling all results
 * @author Matthew Parker
 */
@Entity
public class Compiler {

    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Iterates through a map of answers and questions and adds the result to the aggregate
     *
     * @param s Survey
     * @return Aggregate
     */
    public Aggregate compile(Survey s){
        Aggregate a = new Aggregate();
        List<Form> forms = s.getForms();
        int i = 1;

        Map<Integer, Answer> answers = forms.get(0).getAnswers();
        for(Map.Entry<Integer, Answer> entry : answers.entrySet()) {
            // Not used in Milestone 1
            String type = entry.getValue().getAnswerType();

            ListOfAnswerResult r = new ListOfAnswerResult();
            r.addResponse(entry.getValue().getAnswer());
            a.addResult(r);
        }

        while(i < forms.size()){
            Map<Integer, Answer> answers1 = forms.get(i).getAnswers();
            int itterator  = 0;
            for(Map.Entry<Integer, Answer> entry : answers.entrySet()){
                Result r = a.getResult(itterator);
                r.addResponse(answers1.get(itterator).getAnswer());
                itterator++;
            }
            i++;
        }
        //a.setSurveyID(s.getSurveyID());
        return a;
    }

}
