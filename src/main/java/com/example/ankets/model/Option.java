package com.example.ankets.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String text;
    private boolean checked;

    public Option(String text) {
        this.text = text;
    }

    public Option() {
    }
/*
    @ManyToOne
    @JoinColumn(name = "question_options_id")
    private Question question;

    public Question getQuestionOptions() {
        return question;
    }

    public void setQuestionOptions(Question question) {
        this.question = question;
    }*/

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Option) {
            return id == ((Option) obj).id;
        }
        return false;
    }
}
