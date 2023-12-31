package org.example.questions;

import jakarta.persistence.*;
import org.example.questions.AbstractQuestion;
import org.example.questions.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Multiple Choice Question Class extends AbstractQuestion and implements Question
 */
@Entity
public class MultipleChoiceQuestion extends AbstractQuestion implements Question {
    private String question;
    @ElementCollection
    private List<String> choices;

    /**
     * Class Constructor
     */
    public MultipleChoiceQuestion(){
        choices = new ArrayList<>();
    }

    /**
     * Class Constructor
     */
    public MultipleChoiceQuestion(String question) {
        this();
        this.question = question;
    }

    /**
     * Sets the Question
     * @param q String
     */
    @Override
    public void setQuestion(String q) {
        question = q;
    }

    /**
     * Gets the question
     * @return String
     */
    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public String getType() {
        return "MC";
    }

    /**
     * Adds a choice to the question
     * @param c String
     */
    public void addChoice(String c) {
        choices.add(c);
    }

    /**
     * Returns a list of the choices for the question
     * @return List<String>
     */
    public List<String> getChoices() {
        return choices;
    }

    /**
     * ToString Override
     * @return String
     */
    @Override
    public String toString() {
        String s = question+"\n";
        for (String choice : choices) {
            s = s.concat(choice + "\n");
        }

        return s;
    }
}
