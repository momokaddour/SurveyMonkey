package org.example;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class SurveyManager {
    @OneToMany(cascade = CascadeType.ALL)
    private List<Survey> surveys;
    //add aggregate, compiler, and getters/setters
    @Id
    @GeneratedValue
    private Integer id;

    public SurveyManager() {
        surveys = new ArrayList<>();
    };


    public void compile(Integer id) {
        Survey s = getSurvey(id);

        //Aggregate a = compile(s);
        //aggregate.add(a);
    }

    public void answerSurvey(Integer id, Form f) {
        Survey s = getSurvey(id);
        //s.addForm(f); add when form classes are added to src
    }
    public void addSurvey(Survey s) {
        surveys.add(s);

    }

    public Survey getSurvey(Integer id) {
        for(Survey s : surveys){
            if(Objects.equals(s.getSurveyID(), id)){
                return s;
            }
        }
        return null;
    }

    /**
     * Saves all the surveys on close
     * */
    public void saveSurvey()
    {

    }

    public List<Survey> getSurveys() {
        return surveys;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
