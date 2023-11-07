package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class TextQuestion implements Question{
    private String question;
    @Id
    @GeneratedValue
    private Integer id;

    public TextQuestion() {};
    @Override
    public void setQuestion(String q) {
        question = q;
    }
    @Override
    public String getQuestion() {
        return question;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
