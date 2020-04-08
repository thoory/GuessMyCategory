package com.thomasory.guessmycategory.web.rest;

import com.thomasory.guessmycategory.GuessMyCategoryApp;
import com.thomasory.guessmycategory.domain.OptionalImage;
import com.thomasory.guessmycategory.repository.OptionalImageRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OptionalImageResource} REST controller.
 */
@SpringBootTest(classes = GuessMyCategoryApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class OptionalImageResourceIT {

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    @Autowired
    private OptionalImageRepository optionalImageRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOptionalImageMockMvc;

    private OptionalImage optionalImage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OptionalImage createEntity(EntityManager em) {
        OptionalImage optionalImage = new OptionalImage()
            .link(DEFAULT_LINK);
        return optionalImage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OptionalImage createUpdatedEntity(EntityManager em) {
        OptionalImage optionalImage = new OptionalImage()
            .link(UPDATED_LINK);
        return optionalImage;
    }

    @BeforeEach
    public void initTest() {
        optionalImage = createEntity(em);
    }

    @Test
    @Transactional
    public void createOptionalImage() throws Exception {
        int databaseSizeBeforeCreate = optionalImageRepository.findAll().size();

        // Create the OptionalImage
        restOptionalImageMockMvc.perform(post("/api/optional-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optionalImage)))
            .andExpect(status().isCreated());

        // Validate the OptionalImage in the database
        List<OptionalImage> optionalImageList = optionalImageRepository.findAll();
        assertThat(optionalImageList).hasSize(databaseSizeBeforeCreate + 1);
        OptionalImage testOptionalImage = optionalImageList.get(optionalImageList.size() - 1);
        assertThat(testOptionalImage.getLink()).isEqualTo(DEFAULT_LINK);
    }

    @Test
    @Transactional
    public void createOptionalImageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = optionalImageRepository.findAll().size();

        // Create the OptionalImage with an existing ID
        optionalImage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptionalImageMockMvc.perform(post("/api/optional-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optionalImage)))
            .andExpect(status().isBadRequest());

        // Validate the OptionalImage in the database
        List<OptionalImage> optionalImageList = optionalImageRepository.findAll();
        assertThat(optionalImageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = optionalImageRepository.findAll().size();
        // set the field null
        optionalImage.setLink(null);

        // Create the OptionalImage, which fails.

        restOptionalImageMockMvc.perform(post("/api/optional-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optionalImage)))
            .andExpect(status().isBadRequest());

        List<OptionalImage> optionalImageList = optionalImageRepository.findAll();
        assertThat(optionalImageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOptionalImages() throws Exception {
        // Initialize the database
        optionalImageRepository.saveAndFlush(optionalImage);

        // Get all the optionalImageList
        restOptionalImageMockMvc.perform(get("/api/optional-images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optionalImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)));
    }
    
    @Test
    @Transactional
    public void getOptionalImage() throws Exception {
        // Initialize the database
        optionalImageRepository.saveAndFlush(optionalImage);

        // Get the optionalImage
        restOptionalImageMockMvc.perform(get("/api/optional-images/{id}", optionalImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(optionalImage.getId().intValue()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK));
    }

    @Test
    @Transactional
    public void getNonExistingOptionalImage() throws Exception {
        // Get the optionalImage
        restOptionalImageMockMvc.perform(get("/api/optional-images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOptionalImage() throws Exception {
        // Initialize the database
        optionalImageRepository.saveAndFlush(optionalImage);

        int databaseSizeBeforeUpdate = optionalImageRepository.findAll().size();

        // Update the optionalImage
        OptionalImage updatedOptionalImage = optionalImageRepository.findById(optionalImage.getId()).get();
        // Disconnect from session so that the updates on updatedOptionalImage are not directly saved in db
        em.detach(updatedOptionalImage);
        updatedOptionalImage
            .link(UPDATED_LINK);

        restOptionalImageMockMvc.perform(put("/api/optional-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOptionalImage)))
            .andExpect(status().isOk());

        // Validate the OptionalImage in the database
        List<OptionalImage> optionalImageList = optionalImageRepository.findAll();
        assertThat(optionalImageList).hasSize(databaseSizeBeforeUpdate);
        OptionalImage testOptionalImage = optionalImageList.get(optionalImageList.size() - 1);
        assertThat(testOptionalImage.getLink()).isEqualTo(UPDATED_LINK);
    }

    @Test
    @Transactional
    public void updateNonExistingOptionalImage() throws Exception {
        int databaseSizeBeforeUpdate = optionalImageRepository.findAll().size();

        // Create the OptionalImage

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionalImageMockMvc.perform(put("/api/optional-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optionalImage)))
            .andExpect(status().isBadRequest());

        // Validate the OptionalImage in the database
        List<OptionalImage> optionalImageList = optionalImageRepository.findAll();
        assertThat(optionalImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOptionalImage() throws Exception {
        // Initialize the database
        optionalImageRepository.saveAndFlush(optionalImage);

        int databaseSizeBeforeDelete = optionalImageRepository.findAll().size();

        // Delete the optionalImage
        restOptionalImageMockMvc.perform(delete("/api/optional-images/{id}", optionalImage.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OptionalImage> optionalImageList = optionalImageRepository.findAll();
        assertThat(optionalImageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
