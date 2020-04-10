package com.thomasory.guessmycategory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Session.
 */
@Entity
@Table(name = "session")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    @Column(name = "max_answer")
    private Integer maxAnswer;

    @Column(name = "max_time")
    private Integer maxTime;

    @OneToOne
    @JoinColumn(unique = true)
    private Video video;

    @OneToMany(mappedBy = "session")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Score> scores = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("sessions")
    private Game games;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Session dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getMaxAnswer() {
        return maxAnswer;
    }

    public Session maxAnswer(Integer maxAnswer) {
        this.maxAnswer = maxAnswer;
        return this;
    }

    public void setMaxAnswer(Integer maxAnswer) {
        this.maxAnswer = maxAnswer;
    }

    public Integer getMaxTime() {
        return maxTime;
    }

    public Session maxTime(Integer maxTime) {
        this.maxTime = maxTime;
        return this;
    }

    public void setMaxTime(Integer maxTime) {
        this.maxTime = maxTime;
    }

    public Video getVideo() {
        return video;
    }

    public Session video(Video video) {
        this.video = video;
        return this;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public Session scores(Set<Score> scores) {
        this.scores = scores;
        return this;
    }

    public Session addScores(Score score) {
        this.scores.add(score);
        score.setSession(this);
        return this;
    }

    public Session removeScores(Score score) {
        this.scores.remove(score);
        score.setSession(null);
        return this;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    public Game getGames() {
        return games;
    }

    public Session games(Game game) {
        this.games = game;
        return this;
    }

    public void setGames(Game game) {
        this.games = game;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Session)) {
            return false;
        }
        return id != null && id.equals(((Session) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Session{" +
            "id=" + getId() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", maxAnswer=" + getMaxAnswer() +
            ", maxTime=" + getMaxTime() +
            "}";
    }
}
