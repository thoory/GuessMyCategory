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
 * A Answer.
 */
@Entity
@Table(name = "answer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    @OneToOne
    @JoinColumn(unique = true)
    private Player player;

    @OneToMany(mappedBy = "answers")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Session> sessions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("answers")
    private Keyword keywords;

    @ManyToOne
    @JsonIgnoreProperties("answers")
    private Tag tags;

    @ManyToOne
    @JsonIgnoreProperties("answers")
    private OptionalImage optionalImages;

    @ManyToOne
    @JsonIgnoreProperties("answers")
    private Actor actors;

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

    public Answer dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Player getPlayer() {
        return player;
    }

    public Answer player(Player player) {
        this.player = player;
        return this;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public Answer sessions(Set<Session> sessions) {
        this.sessions = sessions;
        return this;
    }

    public Answer addSession(Session session) {
        this.sessions.add(session);
        session.setAnswers(this);
        return this;
    }

    public Answer removeSession(Session session) {
        this.sessions.remove(session);
        session.setAnswers(null);
        return this;
    }

    public void setSessions(Set<Session> sessions) {
        this.sessions = sessions;
    }

    public Keyword getKeywords() {
        return keywords;
    }

    public Answer keywords(Keyword keyword) {
        this.keywords = keyword;
        return this;
    }

    public void setKeywords(Keyword keyword) {
        this.keywords = keyword;
    }

    public Tag getTags() {
        return tags;
    }

    public Answer tags(Tag tag) {
        this.tags = tag;
        return this;
    }

    public void setTags(Tag tag) {
        this.tags = tag;
    }

    public OptionalImage getOptionalImages() {
        return optionalImages;
    }

    public Answer optionalImages(OptionalImage optionalImage) {
        this.optionalImages = optionalImage;
        return this;
    }

    public void setOptionalImages(OptionalImage optionalImage) {
        this.optionalImages = optionalImage;
    }

    public Actor getActors() {
        return actors;
    }

    public Answer actors(Actor actor) {
        this.actors = actor;
        return this;
    }

    public void setActors(Actor actor) {
        this.actors = actor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Answer)) {
            return false;
        }
        return id != null && id.equals(((Answer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Answer{" +
            "id=" + getId() +
            ", dateCreated='" + getDateCreated() + "'" +
            "}";
    }
}
