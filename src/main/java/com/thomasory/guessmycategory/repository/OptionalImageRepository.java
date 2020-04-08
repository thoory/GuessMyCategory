package com.thomasory.guessmycategory.repository;

import com.thomasory.guessmycategory.domain.OptionalImage;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OptionalImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptionalImageRepository extends JpaRepository<OptionalImage, Long> {
}
