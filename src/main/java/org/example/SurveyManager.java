package org.example;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class SurveyManager {
    @OneToMany(cascade = CascadeType.ALL)
    private List<Survey> surveys;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Aggregate> aggregates;

    @OneToOne
    private Compiler compiler;

    @Id
    @GeneratedValue
    private Integer id;

    public SurveyManager() {
        surveys = new ArrayList<>();
        aggregates = new ArrayList<>();
        compiler = new Compiler();
    };


    public void compile(Integer id) {
        Survey s = getSurvey(id);

        Aggregate aggregate = compiler.compile(s);
        aggregates.add(aggregate);
    }

    public void answerSurvey(Integer id, Form f) {
        Survey s = getSurvey(id);
        s.addForm(f);
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

    public List<Survey> getSurveys() {
        return surveys;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Aggregate getAggregate(Integer id) {
        for(Aggregate a : aggregates){
            if(Objects.equals(a.getSurveyID(), id)){
                return a;
            }
        }
        return null;
    }
}
