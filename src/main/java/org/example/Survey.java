package org.example;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Survey Class
 * @author Kousha Motazedian
 */
@Entity
public class Survey {

    @OneToMany(cascade = CascadeType.ALL)
    private List<Question> questions;
    //add the OneToMany relationship here too
    //private ArrayList<Forms> forms;
    private boolean isOpen;
    @Id
    @GeneratedValue
    @Column(name = "_id")
    private Integer surveyID;

    public Survey() {
        questions = new ArrayList<>();
        //forms forms = new ArrayList<>();
        isOpen = true;
    }

    public void addQuestion(Question q){
        questions.add(q);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setSurveyID(Integer id){
        surveyID = id;
    }

    public Integer getSurveyID() {
        return surveyID;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void close(){
        isOpen = false;
    }
}
