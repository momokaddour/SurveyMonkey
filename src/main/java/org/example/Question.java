package org.example;

import jakarta.persistence.Entity;


public interface Question {
    void setQuestion(String q);
    String getQuestion();
    void setId(Integer id);
    Integer getId();
}
