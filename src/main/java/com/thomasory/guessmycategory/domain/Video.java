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
 * A Video.
 */
@Entity
@Table(name = "video")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    @NotNull
    @Column(name = "iframe", nullable = false)
    private String iframe;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "duration", nullable = false)
    private Duration duration;

    @Column(name = "view")
    private Integer view;

    @Column(name = "jhi_like")
    private Integer like;

    @Column(name = "dislike")
    private Integer dislike;

    @ManyToOne
    @JsonIgnoreProperties("videos")
    private OptionalImage optionalImages;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "video_actors",
               joinColumns = @JoinColumn(name = "video_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "actors_id", referencedColumnName = "id"))
    private Set<Actor> actors = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "video_keywords",
               joinColumns = @JoinColumn(name = "video_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "keywords_id", referencedColumnName = "id"))
    private Set<Keyword> keywords = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "video_tags",
               joinColumns = @JoinColumn(name = "video_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tags_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();

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

    public Video dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getIframe() {
        return iframe;
    }

    public Video iframe(String iframe) {
        this.iframe = iframe;
        return this;
    }

    public void setIframe(String iframe) {
        this.iframe = iframe;
    }

    public String getTitle() {
        return title;
    }

    public Video title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Duration getDuration() {
        return duration;
    }

    public Video duration(Duration duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Integer getView() {
        return view;
    }

    public Video view(Integer view) {
        this.view = view;
        return this;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public Integer getLike() {
        return like;
    }

    public Video like(Integer like) {
        this.like = like;
        return this;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getDislike() {
        return dislike;
    }

    public Video dislike(Integer dislike) {
        this.dislike = dislike;
        return this;
    }

    public void setDislike(Integer dislike) {
        this.dislike = dislike;
    }

    public OptionalImage getOptionalImages() {
        return optionalImages;
    }

    public Video optionalImages(OptionalImage optionalImage) {
        this.optionalImages = optionalImage;
        return this;
    }

    public void setOptionalImages(OptionalImage optionalImage) {
        this.optionalImages = optionalImage;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public Video actors(Set<Actor> actors) {
        this.actors = actors;
        return this;
    }

    public Video addActors(Actor actor) {
        this.actors.add(actor);
        actor.getVideos().add(this);
        return this;
    }

    public Video removeActors(Actor actor) {
        this.actors.remove(actor);
        actor.getVideos().remove(this);
        return this;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public Set<Keyword> getKeywords() {
        return keywords;
    }

    public Video keywords(Set<Keyword> keywords) {
        this.keywords = keywords;
        return this;
    }

    public Video addKeywords(Keyword keyword) {
        this.keywords.add(keyword);
        keyword.getVideos().add(this);
        return this;
    }

    public Video removeKeywords(Keyword keyword) {
        this.keywords.remove(keyword);
        keyword.getVideos().remove(this);
        return this;
    }

    public void setKeywords(Set<Keyword> keywords) {
        this.keywords = keywords;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Video tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public Video addTags(Tag tag) {
        this.tags.add(tag);
        tag.getVideos().add(this);
        return this;
    }

    public Video removeTags(Tag tag) {
        this.tags.remove(tag);
        tag.getVideos().remove(this);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Video)) {
            return false;
        }
        return id != null && id.equals(((Video) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Video{" +
            "id=" + getId() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", iframe='" + getIframe() + "'" +
            ", title='" + getTitle() + "'" +
            ", duration='" + getDuration() + "'" +
            ", view=" + getView() +
            ", like=" + getLike() +
            ", dislike=" + getDislike() +
            "}";
    }
}
