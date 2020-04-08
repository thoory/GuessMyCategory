package com.thomasory.guessmycategory.web.rest;

import com.thomasory.guessmycategory.domain.OptionalImage;
import com.thomasory.guessmycategory.repository.OptionalImageRepository;
import com.thomasory.guessmycategory.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.thomasory.guessmycategory.domain.OptionalImage}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OptionalImageResource {

    private final Logger log = LoggerFactory.getLogger(OptionalImageResource.class);

    private static final String ENTITY_NAME = "optionalImage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OptionalImageRepository optionalImageRepository;

    public OptionalImageResource(OptionalImageRepository optionalImageRepository) {
        this.optionalImageRepository = optionalImageRepository;
    }

    /**
     * {@code POST  /optional-images} : Create a new optionalImage.
     *
     * @param optionalImage the optionalImage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new optionalImage, or with status {@code 400 (Bad Request)} if the optionalImage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/optional-images")
    public ResponseEntity<OptionalImage> createOptionalImage(@Valid @RequestBody OptionalImage optionalImage) throws URISyntaxException {
        log.debug("REST request to save OptionalImage : {}", optionalImage);
        if (optionalImage.getId() != null) {
            throw new BadRequestAlertException("A new optionalImage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OptionalImage result = optionalImageRepository.save(optionalImage);
        return ResponseEntity.created(new URI("/api/optional-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /optional-images} : Updates an existing optionalImage.
     *
     * @param optionalImage the optionalImage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated optionalImage,
     * or with status {@code 400 (Bad Request)} if the optionalImage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the optionalImage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/optional-images")
    public ResponseEntity<OptionalImage> updateOptionalImage(@Valid @RequestBody OptionalImage optionalImage) throws URISyntaxException {
        log.debug("REST request to update OptionalImage : {}", optionalImage);
        if (optionalImage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OptionalImage result = optionalImageRepository.save(optionalImage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, optionalImage.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /optional-images} : get all the optionalImages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of optionalImages in body.
     */
    @GetMapping("/optional-images")
    public List<OptionalImage> getAllOptionalImages() {
        log.debug("REST request to get all OptionalImages");
        return optionalImageRepository.findAll();
    }

    /**
     * {@code GET  /optional-images/:id} : get the "id" optionalImage.
     *
     * @param id the id of the optionalImage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the optionalImage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/optional-images/{id}")
    public ResponseEntity<OptionalImage> getOptionalImage(@PathVariable Long id) {
        log.debug("REST request to get OptionalImage : {}", id);
        Optional<OptionalImage> optionalImage = optionalImageRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(optionalImage);
    }

    /**
     * {@code DELETE  /optional-images/:id} : delete the "id" optionalImage.
     *
     * @param id the id of the optionalImage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/optional-images/{id}")
    public ResponseEntity<Void> deleteOptionalImage(@PathVariable Long id) {
        log.debug("REST request to delete OptionalImage : {}", id);
        optionalImageRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
