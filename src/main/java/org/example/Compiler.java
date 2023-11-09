package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Compiler {

    @Id
    @GeneratedValue
    private Integer id;

    public Aggregate compile(Survey s){
        Aggregate a = new Aggregate();
        List<Form> forms = s.getForms();


        return a;
    }

}
