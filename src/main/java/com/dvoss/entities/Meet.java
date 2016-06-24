package com.dvoss.entities;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by Dan on 6/23/16.
 */
@Entity
@Table(name = "meets")
public class Meet {
    @Id
    @GeneratedValue
    Integer id;

    @Column(nullable = false)
    LocalDate date;

    @Column(nullable = false)
    String location;

    @Column(nullable = false)
    String division;

    @Column(nullable = false)
    String gender;

    @Column(nullable = false)
    String winner;

    @Column(nullable = false)
    String comments;

    @ManyToOne
    User user;

    public Meet() {
    }

    public Meet(LocalDate date, String location, String division, String gender, String winner, String comments, User user) {
        this.date = date;
        this.location = location;
        this.division = division;
        this.gender = gender;
        this.winner = winner;
        this.comments = comments;
        this.user = user;
    }

    public Meet(Integer id, LocalDate date, String location, String division, String gender, String winner, String comments, User user) {
        this.id = id;
        this.date = date;
        this.location = location;
        this.division = division;
        this.gender = gender;
        this.winner = winner;
        this.comments = comments;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
