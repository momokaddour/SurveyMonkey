package org.example.answers;

import jakarta.persistence.*;
import org.example.results.AbstractResult;
import org.example.results.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for having a list of results
 * @author Matthew Parker
 */
@Entity
public class ListOfAnswerResult extends AbstractResult implements Result {

    @ElementCollection
    private List<String> results;

    /**
     * Class constructor
     */
    public ListOfAnswerResult(){
        results = new ArrayList<>();
    }

    /**
     * Adding a response s to the result list
     *
     * @param s String
     */
    public void addResponse(String s) { results.add(s); }


}
