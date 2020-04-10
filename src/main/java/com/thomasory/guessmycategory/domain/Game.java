package com.thomasory.guessmycategory.domain;

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
 * A Game.
 */
@Entity
@Table(name = "game")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "max_user")
    private Integer maxUser;

    @Column(name = "max_video_time")
    private Integer maxVideoTime;

    @OneToMany(mappedBy = "games")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "games")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Session> sessions = new HashSet<>();

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

    public Game dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCode() {
        return code;
    }

    public Game code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getMaxUser() {
        return maxUser;
    }

    public Game maxUser(Integer maxUser) {
        this.maxUser = maxUser;
        return this;
    }

    public void setMaxUser(Integer maxUser) {
        this.maxUser = maxUser;
    }

    public Integer getMaxVideoTime() {
        return maxVideoTime;
    }

    public Game maxVideoTime(Integer maxVideoTime) {
        this.maxVideoTime = maxVideoTime;
        return this;
    }

    public void setMaxVideoTime(Integer maxVideoTime) {
        this.maxVideoTime = maxVideoTime;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Game users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Game addUser(User user) {
        this.users.add(user);
        user.setGames(this);
        return this;
    }

    public Game removeUser(User user) {
        this.users.remove(user);
        user.setGames(null);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public Game sessions(Set<Session> sessions) {
        this.sessions = sessions;
        return this;
    }

    public Game addSession(Session session) {
        this.sessions.add(session);
        session.setGames(this);
        return this;
    }

    public Game removeSession(Session session) {
        this.sessions.remove(session);
        session.setGames(null);
        return this;
    }

    public void setSessions(Set<Session> sessions) {
        this.sessions = sessions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Game)) {
            return false;
        }
        return id != null && id.equals(((Game) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Game{" +
            "id=" + getId() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", code='" + getCode() + "'" +
            ", maxUser=" + getMaxUser() +
            ", maxVideoTime=" + getMaxVideoTime() +
            "}";
    }
}
