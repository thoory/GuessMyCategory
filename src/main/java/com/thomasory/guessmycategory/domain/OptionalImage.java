package com.thomasory.guessmycategory.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A OptionalImage.
 */
@Entity
@Table(name = "optional_image")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OptionalImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "link", nullable = false)
    private String link;

    @OneToMany(mappedBy = "optionalImages")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Session> sessions = new HashSet<>();

    @OneToMany(mappedBy = "optionalImages")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Answer> answers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public OptionalImage link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public OptionalImage sessions(Set<Session> sessions) {
        this.sessions = sessions;
        return this;
    }

    public OptionalImage addSession(Session session) {
        this.sessions.add(session);
        session.setOptionalImages(this);
        return this;
    }

    public OptionalImage removeSession(Session session) {
        this.sessions.remove(session);
        session.setOptionalImages(null);
        return this;
    }

    public void setSessions(Set<Session> sessions) {
        this.sessions = sessions;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public OptionalImage answers(Set<Answer> answers) {
        this.answers = answers;
        return this;
    }

    public OptionalImage addAnswer(Answer answer) {
        this.answers.add(answer);
        answer.setOptionalImages(this);
        return this;
    }

    public OptionalImage removeAnswer(Answer answer) {
        this.answers.remove(answer);
        answer.setOptionalImages(null);
        return this;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OptionalImage)) {
            return false;
        }
        return id != null && id.equals(((OptionalImage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OptionalImage{" +
            "id=" + getId() +
            ", link='" + getLink() + "'" +
            "}";
    }
}
