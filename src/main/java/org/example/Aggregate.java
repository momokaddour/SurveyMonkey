package org.example;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Aggregate {

    @OneToMany(cascade = CascadeType.ALL, targetEntity = AbstractResult.class)
    private List<Result> results;

    @Id
    @GeneratedValue
    @Column(name = "_id")
    private Integer surveyID;

    public Aggregate(){
        results = new ArrayList<>();
    }

    public void addResult(Result r) { results.add(r); }

    public Result getResult(Integer index){ return results.get(index); }

    public Integer getSurveyID() {return surveyID;}

    public void setSurveyID(Integer id){ surveyID = id; }

}
