package org.example.answers;

import jakarta.persistence.*;
import org.example.results.AbstractResult;
import org.example.results.Result;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ListOfAnswerResult extends AbstractResult implements Result {

    @ElementCollection
    private List<String> results;

    public ListOfAnswerResult(){
        results = new ArrayList<>();
    }
    public void addResponse(String s) { results.add(s); }


}
