package com.thomasory.guessmycategory.repository;

import com.thomasory.guessmycategory.domain.Video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Video entity.
 */
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query(value = "select distinct video from Video video left join fetch video.actors left join fetch video.keywords left join fetch video.tags",
        countQuery = "select count(distinct video) from Video video")
    Page<Video> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct video from Video video left join fetch video.actors left join fetch video.keywords left join fetch video.tags")
    List<Video> findAllWithEagerRelationships();

    @Query("select video from Video video left join fetch video.actors left join fetch video.keywords left join fetch video.tags where video.id =:id")
    Optional<Video> findOneWithEagerRelationships(@Param("id") Long id);
}
