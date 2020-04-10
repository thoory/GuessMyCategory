package com.thomasory.guessmycategory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

/**
 * A Score.
 */
@Entity
@Table(name = "score")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    @Column(name = "answer_time_avg")
    private Duration answerTimeAvg;

    @Column(name = "correct_answer")
    private Integer correctAnswer;

    @Column(name = "proposition_total")
    private Integer propositionTotal;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "score")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Answer> answers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("scores")
    private Session session;

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

    public Score dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Duration getAnswerTimeAvg() {
        return answerTimeAvg;
    }

    public Score answerTimeAvg(Duration answerTimeAvg) {
        this.answerTimeAvg = answerTimeAvg;
        return this;
    }

    public void setAnswerTimeAvg(Duration answerTimeAvg) {
        this.answerTimeAvg = answerTimeAvg;
    }

    public Integer getCorrectAnswer() {
        return correctAnswer;
    }

    public Score correctAnswer(Integer correctAnswer) {
        this.correctAnswer = correctAnswer;
        return this;
    }

    public void setCorrectAnswer(Integer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Integer getPropositionTotal() {
        return propositionTotal;
    }

    public Score propositionTotal(Integer propositionTotal) {
        this.propositionTotal = propositionTotal;
        return this;
    }

    public void setPropositionTotal(Integer propositionTotal) {
        this.propositionTotal = propositionTotal;
    }

    public User getUser() {
        return user;
    }

    public Score user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public Score answers(Set<Answer> answers) {
        this.answers = answers;
        return this;
    }

    public Score addAnswers(Answer answer) {
        this.answers.add(answer);
        answer.setScore(this);
        return this;
    }

    public Score removeAnswers(Answer answer) {
        this.answers.remove(answer);
        answer.setScore(null);
        return this;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public Session getSession() {
        return session;
    }

    public Score session(Session session) {
        this.session = session;
        return this;
    }

    public void setSession(Session session) {
        this.session = session;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Score)) {
            return false;
        }
        return id != null && id.equals(((Score) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Score{" +
            "id=" + getId() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", answerTimeAvg='" + getAnswerTimeAvg() + "'" +
            ", correctAnswer=" + getCorrectAnswer() +
            ", propositionTotal=" + getPropositionTotal() +
            "}";
    }
}
