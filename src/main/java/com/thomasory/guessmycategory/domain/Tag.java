package com.thomasory.guessmycategory.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Tag.
 */
@Entity
@Table(name = "tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tag", nullable = false)
    private String tag;

    @OneToMany(mappedBy = "tags")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Session> sessions = new HashSet<>();

    @OneToMany(mappedBy = "tags")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Answer> answers = new HashSet<>();

    @ManyToMany(mappedBy = "tags")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Video> videos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public Tag tag(String tag) {
        this.tag = tag;
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public Tag sessions(Set<Session> sessions) {
        this.sessions = sessions;
        return this;
    }

    public Tag addSession(Session session) {
        this.sessions.add(session);
        session.setTags(this);
        return this;
    }

    public Tag removeSession(Session session) {
        this.sessions.remove(session);
        session.setTags(null);
        return this;
    }

    public void setSessions(Set<Session> sessions) {
        this.sessions = sessions;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public Tag answers(Set<Answer> answers) {
        this.answers = answers;
        return this;
    }

    public Tag addAnswer(Answer answer) {
        this.answers.add(answer);
        answer.setTags(this);
        return this;
    }

    public Tag removeAnswer(Answer answer) {
        this.answers.remove(answer);
        answer.setTags(null);
        return this;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public Set<Video> getVideos() {
        return videos;
    }

    public Tag videos(Set<Video> videos) {
        this.videos = videos;
        return this;
    }

    public Tag addVideos(Video video) {
        this.videos.add(video);
        video.getTags().add(this);
        return this;
    }

    public Tag removeVideos(Video video) {
        this.videos.remove(video);
        video.getTags().remove(this);
        return this;
    }

    public void setVideos(Set<Video> videos) {
        this.videos = videos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tag)) {
            return false;
        }
        return id != null && id.equals(((Tag) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tag{" +
            "id=" + getId() +
            ", tag='" + getTag() + "'" +
            "}";
    }
}
