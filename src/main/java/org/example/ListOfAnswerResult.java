package org.example;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ListOfAnswerResult extends AbstractResult implements Result{

    @ElementCollection
    private List<String> results;

    public ListOfAnswerResult(){
        results = new ArrayList<>();
    }
    public void addResponse(String s) { results.add(s); }


}
