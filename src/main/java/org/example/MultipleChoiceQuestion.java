package org.example;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class MultipleChoiceQuestion implements Question{
    private String question;
    @ElementCollection
    private List<String> choices;
    @Id
    @GeneratedValue
    private Integer id;


    public MultipleChoiceQuestion(){
        choices = new ArrayList<>();
    }


    @Override
    public void setQuestion(String q) {
        question = q;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    public void addChoice(String c) {
        choices.add(c);
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        String s = question+"\n";
        for (String choice : choices) {
            s = s.concat(choice + "\n");
        }

        return s;
    }
}
