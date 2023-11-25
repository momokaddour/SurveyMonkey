package org.example.survey;

import jakarta.persistence.*;
import org.example.questions.AbstractQuestion;
import org.example.questions.Question;
import org.example.survey.Form;
import org.springframework.boot.autoconfigure.web.WebProperties;

import javax.jdo.annotations.PersistenceCapable;
import java.util.ArrayList;
import java.util.List;

/**
 * Survey Class, tracks the questions in the survey and the responses
 * @author Kousha Motazedian
 */
@Entity
@PersistenceCapable
//@Table(name = "survey")
public class Survey {

    @OneToMany(cascade = CascadeType.ALL, targetEntity = AbstractQuestion.class, fetch = FetchType.EAGER)
    private List<Question> questions;
    //add the OneToMany relationship here too
    //private ArrayList<Forms> forms;
    @OneToMany(cascade = CascadeType.ALL, targetEntity = Form.class)
    private List<Form> forms;
    private boolean isOpen;

    public Integer getSurveyID() {
        return surveyID;
    }

    public void setSurveyID(Integer surveyID) {
        this.surveyID = surveyID;
    }

    @Column(name = "_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer surveyID;

    /**
     * Survey Constructor
     */
    public Survey() {
        questions = new ArrayList<>();
        forms = new ArrayList<>();
        isOpen = true;
    }

    /**
     * Adds a question to the survey
     * @param q, Question
     */

    public void addQuestion(Question q){
        questions.add(q);
    }

    /**
     * Returns the list of Questions in the survey
     * @return, List<Question>
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Returns the list of Forms
     * @return, List<Form>
     */
    public List<Form> getForms() { return forms;}

    /**
     * Add a form to the Survey
     * @param f, Form
     */
    public void addForm(Form f)
    {
        forms.add(f);
    }


    /**
     * Checks if the survey is still open
     * @return bool
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Closes the survey
     */
    public void close(){
        isOpen = false;
    }
}
