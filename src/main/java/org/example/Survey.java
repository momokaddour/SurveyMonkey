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

    @OneToMany(cascade = CascadeType.ALL, targetEntity = AbstractQuestion.class)
    private List<Question> questions;
    //add the OneToMany relationship here too
    //private ArrayList<Forms> forms;
    @OneToMany(cascade = CascadeType.ALL, targetEntity = Form.class)
    private List<Form> forms;
    private boolean isOpen;
    @Id
    @GeneratedValue
    @Column(name = "_id")
    private Integer surveyID;

    public Survey() {
        questions = new ArrayList<>();
        forms = new ArrayList<>();
        isOpen = true;
    }

    public void addQuestion(Question q){
        questions.add(q);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<Form> getForms() { return forms;}

    public void addForm(Form f) { forms.add(f);}

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
