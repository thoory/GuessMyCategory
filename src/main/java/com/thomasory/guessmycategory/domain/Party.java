package com.thomasory.guessmycategory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A Party.
 */
@Entity
@Table(name = "party")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Party implements Serializable {

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

    @Column(name = "max_player")
    private Integer maxPlayer;

    @Column(name = "max_video_time")
    private Integer maxVideoTime;

    @ManyToOne
    @JsonIgnoreProperties("parties")
    private Session sessions;

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

    public Party dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCode() {
        return code;
    }

    public Party code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getMaxPlayer() {
        return maxPlayer;
    }

    public Party maxPlayer(Integer maxPlayer) {
        this.maxPlayer = maxPlayer;
        return this;
    }

    public void setMaxPlayer(Integer maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    public Integer getMaxVideoTime() {
        return maxVideoTime;
    }

    public Party maxVideoTime(Integer maxVideoTime) {
        this.maxVideoTime = maxVideoTime;
        return this;
    }

    public void setMaxVideoTime(Integer maxVideoTime) {
        this.maxVideoTime = maxVideoTime;
    }

    public Session getSessions() {
        return sessions;
    }

    public Party sessions(Session session) {
        this.sessions = session;
        return this;
    }

    public void setSessions(Session session) {
        this.sessions = session;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Party)) {
            return false;
        }
        return id != null && id.equals(((Party) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Party{" +
            "id=" + getId() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", code='" + getCode() + "'" +
            ", maxPlayer=" + getMaxPlayer() +
            ", maxVideoTime=" + getMaxVideoTime() +
            "}";
    }
}
