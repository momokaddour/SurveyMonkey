package org.example;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SurveyManager Class, manages all the surveys and can compile their results
 * @author Kousha Motazedian
 */
@Entity
public class SurveyManager {
    @OneToMany(cascade = CascadeType.ALL)
    private List<Survey> surveys;
    //add aggregate, compiler, and getters/setters
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Constructor for the survey manager
     */
    public SurveyManager() {
        surveys = new ArrayList<>();
    };


    /**
     * Compiles the results of a survey based on its ID
     * @param id, Integer
     */
    public void compile(Integer id) {
        Survey s = getSurvey(id);

        //Aggregate a = compile(s);
        //aggregate.add(a);
    }

    /**
     * Answering a specific survey
     * @param id Integer
     * @param f Form
     */
    public void answerSurvey(Integer id, Form f) {
        Survey s = getSurvey(id);
        //s.addForm(f); add when form classes are added to src
    }

    /**
     * Add a survey to be managed by the manager
     * @param s Survey
     */
    public void addSurvey(Survey s) {
        surveys.add(s);
    }

    /**
     * Get a survey based on its ID
     * @param id, Integer
     * @return Survey
     */
    public Survey getSurvey(Integer id) {
        for(Survey s : surveys){
            if(Objects.equals(s.getSurveyID(), id)){
                return s;
            }
        }
        return null;
    }

    /**
     * Returns a list of all surveys managed
     * @return List<Surveys>
     */
    public List<Survey> getSurveys() {
        return surveys;
    }

    /**
     * Set the ID of the Survey Manager
     * @param id Integer
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get the ID of the Survey Manager
     * @return Integer
     */
    public Integer getId() {
        return id;
    }
}
