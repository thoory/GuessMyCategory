package com.thomasory.guessmycategory.web.rest;

import com.thomasory.guessmycategory.GuessMyCategoryApp;
import com.thomasory.guessmycategory.domain.Video;
import com.thomasory.guessmycategory.repository.VideoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VideoResource} REST controller.
 */
@SpringBootTest(classes = GuessMyCategoryApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class VideoResourceIT {

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_IFRAME = "AAAAAAAAAA";
    private static final String UPDATED_IFRAME = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Duration DEFAULT_DURATION = Duration.ofHours(6);
    private static final Duration UPDATED_DURATION = Duration.ofHours(12);

    private static final Integer DEFAULT_VIEW = 1;
    private static final Integer UPDATED_VIEW = 2;

    private static final Integer DEFAULT_LIKE = 1;
    private static final Integer UPDATED_LIKE = 2;

    private static final Integer DEFAULT_DISLIKE = 1;
    private static final Integer UPDATED_DISLIKE = 2;

    @Autowired
    private VideoRepository videoRepository;

    @Mock
    private VideoRepository videoRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVideoMockMvc;

    private Video video;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Video createEntity(EntityManager em) {
        Video video = new Video()
            .dateCreated(DEFAULT_DATE_CREATED)
            .iframe(DEFAULT_IFRAME)
            .title(DEFAULT_TITLE)
            .duration(DEFAULT_DURATION)
            .view(DEFAULT_VIEW)
            .like(DEFAULT_LIKE)
            .dislike(DEFAULT_DISLIKE);
        return video;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Video createUpdatedEntity(EntityManager em) {
        Video video = new Video()
            .dateCreated(UPDATED_DATE_CREATED)
            .iframe(UPDATED_IFRAME)
            .title(UPDATED_TITLE)
            .duration(UPDATED_DURATION)
            .view(UPDATED_VIEW)
            .like(UPDATED_LIKE)
            .dislike(UPDATED_DISLIKE);
        return video;
    }

    @BeforeEach
    public void initTest() {
        video = createEntity(em);
    }

    @Test
    @Transactional
    public void createVideo() throws Exception {
        int databaseSizeBeforeCreate = videoRepository.findAll().size();

        // Create the Video
        restVideoMockMvc.perform(post("/api/videos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(video)))
            .andExpect(status().isCreated());

        // Validate the Video in the database
        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeCreate + 1);
        Video testVideo = videoList.get(videoList.size() - 1);
        assertThat(testVideo.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testVideo.getIframe()).isEqualTo(DEFAULT_IFRAME);
        assertThat(testVideo.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testVideo.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testVideo.getView()).isEqualTo(DEFAULT_VIEW);
        assertThat(testVideo.getLike()).isEqualTo(DEFAULT_LIKE);
        assertThat(testVideo.getDislike()).isEqualTo(DEFAULT_DISLIKE);
    }

    @Test
    @Transactional
    public void createVideoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = videoRepository.findAll().size();

        // Create the Video with an existing ID
        video.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVideoMockMvc.perform(post("/api/videos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(video)))
            .andExpect(status().isBadRequest());

        // Validate the Video in the database
        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoRepository.findAll().size();
        // set the field null
        video.setDateCreated(null);

        // Create the Video, which fails.

        restVideoMockMvc.perform(post("/api/videos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(video)))
            .andExpect(status().isBadRequest());

        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIframeIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoRepository.findAll().size();
        // set the field null
        video.setIframe(null);

        // Create the Video, which fails.

        restVideoMockMvc.perform(post("/api/videos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(video)))
            .andExpect(status().isBadRequest());

        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoRepository.findAll().size();
        // set the field null
        video.setTitle(null);

        // Create the Video, which fails.

        restVideoMockMvc.perform(post("/api/videos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(video)))
            .andExpect(status().isBadRequest());

        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoRepository.findAll().size();
        // set the field null
        video.setDuration(null);

        // Create the Video, which fails.

        restVideoMockMvc.perform(post("/api/videos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(video)))
            .andExpect(status().isBadRequest());

        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVideos() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        // Get all the videoList
        restVideoMockMvc.perform(get("/api/videos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(video.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].iframe").value(hasItem(DEFAULT_IFRAME)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].view").value(hasItem(DEFAULT_VIEW)))
            .andExpect(jsonPath("$.[*].like").value(hasItem(DEFAULT_LIKE)))
            .andExpect(jsonPath("$.[*].dislike").value(hasItem(DEFAULT_DISLIKE)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllVideosWithEagerRelationshipsIsEnabled() throws Exception {
        VideoResource videoResource = new VideoResource(videoRepositoryMock);
        when(videoRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVideoMockMvc.perform(get("/api/videos?eagerload=true"))
            .andExpect(status().isOk());

        verify(videoRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllVideosWithEagerRelationshipsIsNotEnabled() throws Exception {
        VideoResource videoResource = new VideoResource(videoRepositoryMock);
        when(videoRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVideoMockMvc.perform(get("/api/videos?eagerload=true"))
            .andExpect(status().isOk());

        verify(videoRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getVideo() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        // Get the video
        restVideoMockMvc.perform(get("/api/videos/{id}", video.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(video.getId().intValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.iframe").value(DEFAULT_IFRAME))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.toString()))
            .andExpect(jsonPath("$.view").value(DEFAULT_VIEW))
            .andExpect(jsonPath("$.like").value(DEFAULT_LIKE))
            .andExpect(jsonPath("$.dislike").value(DEFAULT_DISLIKE));
    }

    @Test
    @Transactional
    public void getNonExistingVideo() throws Exception {
        // Get the video
        restVideoMockMvc.perform(get("/api/videos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVideo() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        int databaseSizeBeforeUpdate = videoRepository.findAll().size();

        // Update the video
        Video updatedVideo = videoRepository.findById(video.getId()).get();
        // Disconnect from session so that the updates on updatedVideo are not directly saved in db
        em.detach(updatedVideo);
        updatedVideo
            .dateCreated(UPDATED_DATE_CREATED)
            .iframe(UPDATED_IFRAME)
            .title(UPDATED_TITLE)
            .duration(UPDATED_DURATION)
            .view(UPDATED_VIEW)
            .like(UPDATED_LIKE)
            .dislike(UPDATED_DISLIKE);

        restVideoMockMvc.perform(put("/api/videos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVideo)))
            .andExpect(status().isOk());

        // Validate the Video in the database
        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeUpdate);
        Video testVideo = videoList.get(videoList.size() - 1);
        assertThat(testVideo.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testVideo.getIframe()).isEqualTo(UPDATED_IFRAME);
        assertThat(testVideo.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testVideo.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testVideo.getView()).isEqualTo(UPDATED_VIEW);
        assertThat(testVideo.getLike()).isEqualTo(UPDATED_LIKE);
        assertThat(testVideo.getDislike()).isEqualTo(UPDATED_DISLIKE);
    }

    @Test
    @Transactional
    public void updateNonExistingVideo() throws Exception {
        int databaseSizeBeforeUpdate = videoRepository.findAll().size();

        // Create the Video

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVideoMockMvc.perform(put("/api/videos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(video)))
            .andExpect(status().isBadRequest());

        // Validate the Video in the database
        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVideo() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        int databaseSizeBeforeDelete = videoRepository.findAll().size();

        // Delete the video
        restVideoMockMvc.perform(delete("/api/videos/{id}", video.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
