package org.example.results;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import org.example.questions.MultipleChoiceQuestion;
import java.util.HashMap;
import java.util.Map;

@Entity
public class PieChart extends AbstractResult implements Result{

    @ElementCollection
    private Map<String, Integer> answerCount;
    private String question;
    private int numAnswers;

    /**
     * Constructor that takes in the question and initializes map.
     *
     * @param question
     * */
    public PieChart(MultipleChoiceQuestion question)
    {
        this.question = question.getQuestion();
        this.numAnswers = 0;
        this.answerCount = new HashMap<>();
        for (String s : question.getChoices()) {
            answerCount.put(s, 0);
        }
    }

    /**
     * Default Constructor
     * */
    public PieChart() {
        this.answerCount = new HashMap<>();
    }

    /**
     * Adds a response to the map of String s
     *
     * @param s
     * */
    @Override
    public void addResponse(String s) {
        answerCount.put(s, answerCount.get(s) + 1);
    }

    /**
     * Getter for the question associated with the result
     *
     * @return String
     * */
    public String getQuestion() {
        return question;
    }

    /**
     * Setter for the question associated with the result
     *
     * @param question
     * */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Getter for the map holding the count for each result
     *
     * @return Map of answers mapped to the amount of time that option was chosen
     * */
    public Map<String, Integer> getAnswerCount()
    {
        return this.answerCount;
    }
}
