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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "session_players",
               joinColumns = @JoinColumn(name = "session_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "players_id", referencedColumnName = "id"))
    private Set<Player> players = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("sessions")
    private Keyword keywords;

    @ManyToOne
    @JsonIgnoreProperties("sessions")
    private Tag tags;

    @ManyToOne
    @JsonIgnoreProperties("sessions")
    private OptionalImage optionalImages;

    @ManyToOne
    @JsonIgnoreProperties("sessions")
    private Actor actors;

    @ManyToOne
    @JsonIgnoreProperties("sessions")
    private Answer answers;

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

    public Set<Player> getPlayers() {
        return players;
    }

    public Session players(Set<Player> players) {
        this.players = players;
        return this;
    }

    public Session addPlayers(Player player) {
        this.players.add(player);
        player.getSessions().add(this);
        return this;
    }

    public Session removePlayers(Player player) {
        this.players.remove(player);
        player.getSessions().remove(this);
        return this;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public Keyword getKeywords() {
        return keywords;
    }

    public Session keywords(Keyword keyword) {
        this.keywords = keyword;
        return this;
    }

    public void setKeywords(Keyword keyword) {
        this.keywords = keyword;
    }

    public Tag getTags() {
        return tags;
    }

    public Session tags(Tag tag) {
        this.tags = tag;
        return this;
    }

    public void setTags(Tag tag) {
        this.tags = tag;
    }

    public OptionalImage getOptionalImages() {
        return optionalImages;
    }

    public Session optionalImages(OptionalImage optionalImage) {
        this.optionalImages = optionalImage;
        return this;
    }

    public void setOptionalImages(OptionalImage optionalImage) {
        this.optionalImages = optionalImage;
    }

    public Actor getActors() {
        return actors;
    }

    public Session actors(Actor actor) {
        this.actors = actor;
        return this;
    }

    public void setActors(Actor actor) {
        this.actors = actor;
    }

    public Answer getAnswers() {
        return answers;
    }

    public Session answers(Answer answer) {
        this.answers = answer;
        return this;
    }

    public void setAnswers(Answer answer) {
        this.answers = answer;
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
            "}";
    }
}
