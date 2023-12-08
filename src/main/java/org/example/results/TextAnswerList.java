package org.example.results;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import org.example.questions.TextQuestion;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TextAnswerList extends AbstractResult implements Result{
    @ElementCollection
    private List<String> answerList;
    private String question;

    /**
     * Default Constructor for TextAnswerList
     */
    public TextAnswerList(){
        this.answerList = new ArrayList<>();
    }

    /**
     * Constructor for TextAnswerList
     *
     * @param q of type TextQuestion
     */
    public TextAnswerList(TextQuestion q)
    {
        this.answerList = new ArrayList<>();
        this.question = q.getQuestion();
    }

    /**
     * Adds response of type string to the list of responses
     * @param s
     */
    @Override
    public void addResponse(String s) {
        this.answerList.add(s);
    }

    /**
     * Returns the list of answers, a list of strings
     *
     * @return List of Strings
     */
    public List<String> getAnswerList() {
        return this.answerList;
    }

    /**
     * Getter for question associated with result
     *
     * @return String
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Setter for question associated with result
     *
     * @param question String
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public String createChart() {
        return "";
    }
}
