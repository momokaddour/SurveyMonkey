package org.example.objectdb;

import org.example.answers.Answer;
import org.example.answers.TextAnswer;
import org.example.questions.Question;
import org.example.questions.TextQuestion;
import org.example.repos.SurveyRepo;
import org.example.survey.Form;
import org.example.survey.Survey;
import org.example.survey.SurveyManager;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.util.List;

@EntityScan
public class ObjectDB {
    private EntityManagerFactory surveyFactory;
    private EntityManager surveyManager;

    public ObjectDB()
    {
        this.surveyFactory = Persistence.createEntityManagerFactory("survey.odb");
        this.surveyManager = surveyFactory.createEntityManager();
    }

    public void saveSurvey(Survey survey) {
        surveyManager.getTransaction().begin();
        surveyManager.persist(survey);
        surveyManager.getTransaction().commit();
    }

    public Survey getSurvey(Integer id)
    {
        surveyManager.getTransaction().begin();
        TypedQuery<Survey> surveyTypedQuery = surveyManager.createQuery("SELECT s FROM Survey s WHERE surveyID = :find", Survey.class)
                .setParameter("find", id);
        Survey survey = surveyTypedQuery.getSingleResult();
        surveyManager.getTransaction().commit();

        return survey;
    }

    public void saveQuestion(Question question) {
        surveyManager.getTransaction().begin();
        surveyManager.persist(question);
        surveyManager.getTransaction().commit();
    }

    public void saveForm(Form form) {
        surveyManager.getTransaction().begin();
        surveyManager.persist(form);
        surveyManager.getTransaction().commit();
    }

    public void saveAnswer(Answer answer) {
        surveyManager.getTransaction().begin();
        surveyManager.persist(answer);
        surveyManager.getTransaction().commit();
    }

    public Form getForm(Integer id)
    {
        surveyManager.getTransaction().begin();
        //Form form = surveyManager.getReference(Form.class, id);
        TypedQuery<Form> formTypedQuery = surveyManager.createQuery("SELECT f FROM Form f WHERE formID = :find", Form.class)
                .setParameter("find", id);
        Form form = formTypedQuery.getSingleResult();
        surveyManager.getTransaction().commit();

        return form;
    }

    public Question getTextQuestion(Integer id)
    {
        surveyManager.getTransaction().begin();
        //Form form = surveyManager.getReference(Form.class, id);
        TypedQuery<TextQuestion> questionQuery =
                surveyManager.createQuery("SELECT q FROM TextQuestion q WHERE questionId = :find", TextQuestion.class)
                .setParameter("find", id);
        Question question = questionQuery.getSingleResult();
        surveyManager.getTransaction().commit();

        return question;
    }

    public void deleteAll()
    {
        TypedQuery<Survey> surveyQuery =
                surveyManager.createQuery("SELECT s FROM Survey s", Survey.class);
        List<Survey> surveyResults = surveyQuery.getResultList();

        TypedQuery<Question> questionQuery =
                surveyManager.createQuery("SELECT q FROM Object q where q instanceof Question", Question.class);
        List<Question> questionResults = questionQuery.getResultList();

        surveyManager.getTransaction().begin();
        for (Survey survey : surveyResults)
        {
            surveyManager.remove(survey);
        }
        surveyManager.getTransaction().commit();

        surveyManager.getTransaction().begin();
        for (Question q : questionResults)
        {
            surveyManager.remove(q);
        }
        surveyManager.getTransaction().commit();
    }

}