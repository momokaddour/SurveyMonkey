package org.example.results;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import org.example.questions.NumberRangeQuestion;
import org.example.questions.Question;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Histogram extends AbstractResult implements Result{
    @ElementCollection
    private Map<String, Integer> answerCount;
    private String question;

    /**
     * Constructor that takes in the question to initializes the map and String question
     *
     * @param q the question
     * */
    public Histogram(NumberRangeQuestion q)
    {
        this.question = q.getQuestion();
        this.answerCount = new HashMap<>();
    }

    /**
     * Default constructor
     * */
    public Histogram()
    {
        this.answerCount = new HashMap<>();
    }

    /**
     * Adds response to map or updates the response count if it already exists.
     *
     * @param s to add to map
     * */
    @Override
    public void addResponse(String s) {
        if (this.answerCount.containsKey(s))
        {
            this.answerCount.put(s, this.answerCount.get(s) + 1);
        }
        else
        {
            this.answerCount.put(s, 1);
        }
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
     * Gets map for answers and how many times they are chosen
     *
     * @return map
     * */
    public Map<String, Integer> getAnswerCount() {
        return answerCount;
    }
}
