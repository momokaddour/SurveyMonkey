package org.example;

import org.example.survey.Survey;
import org.example.survey.SurveyManager;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        EntityManagerFactory surveyFactory = Persistence.createEntityManagerFactory("survey.odb");
        EntityManager surveyManager = surveyFactory.createEntityManager();
        surveyManager.getTransaction().begin();
        Survey survey = new Survey();
        surveyManager.persist(survey);
        surveyManager.getTransaction().commit();
    }
}