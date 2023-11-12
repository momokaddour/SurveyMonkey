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

@Entity
public class Compiler {

    @Id
    @GeneratedValue
    private Integer id;

    public Aggregate compile(Survey s){
        Aggregate a = new Aggregate();
        List<Form> forms = s.getForms();
        int i = 0;

        Map<Integer, Answer> answers = forms.get(i).getAnswers();
        for(int j = 0; j < answers.size(); j++){
            String type = answers.get(j).getAnswerType();

            ListOfAnswerResult r = new ListOfAnswerResult();
            r.addResponse(answers.get(j).getAnswer());
            a.addResult(r);
        }

        while(i < forms.size()){
            Map<Integer, Answer> answers1 = forms.get(i).getAnswers();
            for(int x = 0; x < answers1.size(); x++){
                Result r = a.getResult(x);
                r.addResponse(answers1.get(x).getAnswer());
            }
            i++;
        }

        return a;
    }

}
