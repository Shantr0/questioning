package com.example.ankets.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Question {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String question;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Option> options;

  /*  @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;*/

    private boolean multiselect;

    public boolean isMultiselect() {
        return multiselect;
    }

    public void setMultiselect(boolean multiselect) {
        this.multiselect = multiselect;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @Override
    protected Object clone() {
        Question clone = new Question();
        clone.setQuestion(question);
        clone.setMultiselect(multiselect);
        return clone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Question) {
            return id == ((Question) obj).id;
        }
        return false;
    }
}
