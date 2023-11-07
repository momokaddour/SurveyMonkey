package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class TextQuestion extends AbstractQuestion implements Question{
    private String question;
    public TextQuestion() {};
    @Override
    public void setQuestion(String q) {
        question = q;
    }
    @Override
    public String getQuestion() {
        return question;
    }
    
}
