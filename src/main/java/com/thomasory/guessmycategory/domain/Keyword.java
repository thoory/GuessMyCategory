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
 * A Keyword.
 */
@Entity
@Table(name = "keyword")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Keyword implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "keyword", nullable = false)
    private String keyword;

    @ManyToMany(mappedBy = "keywords")
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

    public String getKeyword() {
        return keyword;
    }

    public Keyword keyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Set<Video> getVideos() {
        return videos;
    }

    public Keyword videos(Set<Video> videos) {
        this.videos = videos;
        return this;
    }

    public Keyword addVideos(Video video) {
        this.videos.add(video);
        video.getKeywords().add(this);
        return this;
    }

    public Keyword removeVideos(Video video) {
        this.videos.remove(video);
        video.getKeywords().remove(this);
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
        if (!(o instanceof Keyword)) {
            return false;
        }
        return id != null && id.equals(((Keyword) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Keyword{" +
            "id=" + getId() +
            ", keyword='" + getKeyword() + "'" +
            "}";
    }
}
