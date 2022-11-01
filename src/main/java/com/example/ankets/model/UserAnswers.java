package com.example.ankets.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserAnswers extends Question {

    public UserAnswers(Question question) {
        setOptions(question.getOptions());
        setQuestion(question.getQuestion());
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserAnswers() {

    }

    public UserAnswers(User user, Question questionOption) {
        this.user = user;
        setQuestion(questionOption.getQuestion());
        setMultiselect(questionOption.isMultiselect());
        //setOptions(questionOption.getOptions());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
