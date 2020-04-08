package com.thomasory.guessmycategory.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
import java.time.Duration;

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
