package com.thomasory.guessmycategory.web.rest;

import com.thomasory.guessmycategory.domain.Answer;
import com.thomasory.guessmycategory.repository.AnswerRepository;
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
 * REST controller for managing {@link com.thomasory.guessmycategory.domain.Answer}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AnswerResource {

    private final Logger log = LoggerFactory.getLogger(AnswerResource.class);

    private static final String ENTITY_NAME = "answer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnswerRepository answerRepository;

    public AnswerResource(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    /**
     * {@code POST  /answers} : Create a new answer.
     *
     * @param answer the answer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new answer, or with status {@code 400 (Bad Request)} if the answer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/answers")
    public ResponseEntity<Answer> createAnswer(@Valid @RequestBody Answer answer) throws URISyntaxException {
        log.debug("REST request to save Answer : {}", answer);
        if (answer.getId() != null) {
            throw new BadRequestAlertException("A new answer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Answer result = answerRepository.save(answer);
        return ResponseEntity.created(new URI("/api/answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /answers} : Updates an existing answer.
     *
     * @param answer the answer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated answer,
     * or with status {@code 400 (Bad Request)} if the answer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the answer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/answers")
    public ResponseEntity<Answer> updateAnswer(@Valid @RequestBody Answer answer) throws URISyntaxException {
        log.debug("REST request to update Answer : {}", answer);
        if (answer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Answer result = answerRepository.save(answer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, answer.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /answers} : get all the answers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of answers in body.
     */
    @GetMapping("/answers")
    public List<Answer> getAllAnswers() {
        log.debug("REST request to get all Answers");
        return answerRepository.findAll();
    }

    /**
     * {@code GET  /answers/:id} : get the "id" answer.
     *
     * @param id the id of the answer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the answer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/answers/{id}")
    public ResponseEntity<Answer> getAnswer(@PathVariable Long id) {
        log.debug("REST request to get Answer : {}", id);
        Optional<Answer> answer = answerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(answer);
    }

    /**
     * {@code DELETE  /answers/:id} : delete the "id" answer.
     *
     * @param id the id of the answer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/answers/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        log.debug("REST request to delete Answer : {}", id);
        answerRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
