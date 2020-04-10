package com.thomasory.guessmycategory.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Actor.
 */
@Entity
@Table(name = "actor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Actor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "popularity")
    private Integer popularity;

    @Column(name = "contry")
    private String contry;

    @Column(name = "link")
    private String link;

    @ManyToOne
    @JsonIgnoreProperties("actors")
    private OptionalImage images;

    @ManyToMany(mappedBy = "actors")
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

    public String getName() {
        return name;
    }

    public Actor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public Actor popularity(Integer popularity) {
        this.popularity = popularity;
        return this;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public String getContry() {
        return contry;
    }

    public Actor contry(String contry) {
        this.contry = contry;
        return this;
    }

    public void setContry(String contry) {
        this.contry = contry;
    }

    public String getLink() {
        return link;
    }

    public Actor link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public OptionalImage getImages() {
        return images;
    }

    public Actor images(OptionalImage optionalImage) {
        this.images = optionalImage;
        return this;
    }

    public void setImages(OptionalImage optionalImage) {
        this.images = optionalImage;
    }

    public Set<Video> getVideos() {
        return videos;
    }

    public Actor videos(Set<Video> videos) {
        this.videos = videos;
        return this;
    }

    public Actor addVideos(Video video) {
        this.videos.add(video);
        video.getActors().add(this);
        return this;
    }

    public Actor removeVideos(Video video) {
        this.videos.remove(video);
        video.getActors().remove(this);
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
        if (!(o instanceof Actor)) {
            return false;
        }
        return id != null && id.equals(((Actor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Actor{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", popularity=" + getPopularity() +
            ", contry='" + getContry() + "'" +
            ", link='" + getLink() + "'" +
            "}";
    }
}
