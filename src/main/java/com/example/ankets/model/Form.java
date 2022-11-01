package com.example.ankets.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "FORM")
public class Form {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Question> questionOptions;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestionOptions() {
        return questionOptions;
    }

    public void setQuestionOptions(List<Question> questionOptions) {
        this.questionOptions = questionOptions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Form) {
            return id == ((Form) obj).id;
        }
        return false;
    }
}
