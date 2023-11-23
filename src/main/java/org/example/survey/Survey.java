package org.example.survey;

import jakarta.persistence.*;
import org.example.questions.AbstractQuestion;
import org.example.questions.Question;
import org.example.survey.Form;

import java.util.ArrayList;
import java.util.List;

/**
 * Survey Class, tracks the questions in the survey and the responses
 * @author Kousha Motazedian
 */
@Entity
@Table(name = "survey")
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

    public String getQuestionString() {
        String s = "";
        for (Question question : questions) {
            s = s.concat(question.getQuestion() + "\n");

        }
        return s;
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
     * Set the id for the survey
     * @param id, Integer
     */

    public void setSurveyID(Integer id){
        surveyID = id;
    }

    /**
     * Get the survey ID
     * @return Integer
     */
    public Integer getSurveyID() {
        return surveyID;
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
