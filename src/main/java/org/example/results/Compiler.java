package org.example.results;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.example.answers.*;
import org.example.questions.MultipleChoiceQuestion;
import org.example.questions.NumberRangeQuestion;
import org.example.questions.Question;
import org.example.questions.TextQuestion;
import org.example.survey.Form;
import org.example.survey.Survey;

import java.util.*;

/**
 * Responsible for compiling all results
 * @author Matthew Parker
 */
@Entity
public class Compiler {

    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Helper method that simply takes the list of questions from the surveys and returns the requested ID
     *
     * @param id of question to find
     * @param questions the list of questions to search
     * @return Question
     * */
    private Question findQuestionFromID(List<Question> questions, Integer id)
    {
        for (Question q : questions)
        {
            if (Objects.equals(q.getId(), id))
            {
                return q;
            }
        }

        return new MultipleChoiceQuestion();
    }

    /**
     * Iterates through a map of answers and questions and adds the result to the aggregate
     *
     * @param s Survey
     * @return Aggregate
     */
    public Aggregate compile(Survey s){
        Aggregate a = new Aggregate();
        List<Form> forms = s.getForms();
        Map<Integer, Result> results = new HashMap<>();
        boolean initialRun = true;

        //Iterate through all the forms associated with the survey
        for (Form f: forms)
        {
            Map<Integer, Answer> answers = f.getAnswers();

            //Iterate through all the answers in the current form
            for (int qNum : answers.keySet())
            {
                Answer answer = answers.get(qNum);
                Question question = findQuestionFromID(s.getQuestions(), qNum);

                if (answer instanceof MCAnswer)
                {
                    //If it's the first form being looked at, register the results in the hashmap
                    if (initialRun)
                    {
                        results.put(qNum, new PieChart((MultipleChoiceQuestion) question));
                    }

                    //Update the results with the current form's answer for that specific question.
                    results.get(qNum).addResponse(answer.getAnswer());
                }
                else if (answer instanceof TextAnswer)
                {
                    if (initialRun)
                    {
                        results.put(qNum, new TextAnswerList((TextQuestion) question));
                    }

                    results.get(qNum).addResponse(answer.getAnswer());

                }
                else if (answer instanceof NumberRangeAnswer)
                {
                    if (initialRun)
                    {
                        results.put(qNum, new Histogram((NumberRangeQuestion) question));
                    }

                    results.get(qNum).addResponse(answer.getAnswer());
                }
            }

            //initialRun is set to false after the first form establishes a template of sorts.
            if (initialRun && !f.getAnswers().isEmpty())
            {
                initialRun = false;
            }
        }

        //Add all the results to the aggregate and return it.
        for (Result r : results.values())
        {
            a.addResult(r);
            a.addImageNames(r.createChart());
        }

        return a;
    }

}
