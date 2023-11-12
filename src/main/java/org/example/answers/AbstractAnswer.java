package org.example.answers;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class AbstractAnswer {
    @Id
    @GeneratedValue
    private Integer id;

    public void setId() {this.id = id;}

    public Integer getId() { return id; }
}
