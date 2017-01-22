package net.testaholic_acme_site.web.rest;

import net.testaholic_acme_site.AcmeSiteApp;
import net.testaholic_acme_site.domain.ThVideoInput;
import net.testaholic_acme_site.repository.ThVideoInputRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ThVideoInputResource REST controller.
 *
 * @see ThVideoInputResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AcmeSiteApp.class)
@WebAppConfiguration
@IntegrationTest
public class ThVideoInputResourceIntTest {


    private static final byte[] DEFAULT_VIDEO_INPUT_FIELD = TestUtil.createByteArray(50, "0");
    private static final byte[] UPDATED_VIDEO_INPUT_FIELD = TestUtil.createByteArray(10000000, "1");
    private static final String DEFAULT_VIDEO_INPUT_FIELD_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_VIDEO_INPUT_FIELD_CONTENT_TYPE = "image/png";

    @Inject
    private ThVideoInputRepository thVideoInputRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restThVideoInputMockMvc;

    private ThVideoInput thVideoInput;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThVideoInputResource thVideoInputResource = new ThVideoInputResource();
        ReflectionTestUtils.setField(thVideoInputResource, "thVideoInputRepository", thVideoInputRepository);
        this.restThVideoInputMockMvc = MockMvcBuilders.standaloneSetup(thVideoInputResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        thVideoInput = new ThVideoInput();
        thVideoInput.setVideoInputField(DEFAULT_VIDEO_INPUT_FIELD);
        thVideoInput.setVideoInputFieldContentType(DEFAULT_VIDEO_INPUT_FIELD_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createThVideoInput() throws Exception {
        int databaseSizeBeforeCreate = thVideoInputRepository.findAll().size();

        // Create the ThVideoInput

        restThVideoInputMockMvc.perform(post("/api/th-video-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(thVideoInput)))
                .andExpect(status().isCreated());

        // Validate the ThVideoInput in the database
        List<ThVideoInput> thVideoInputs = thVideoInputRepository.findAll();
        assertThat(thVideoInputs).hasSize(databaseSizeBeforeCreate + 1);
        ThVideoInput testThVideoInput = thVideoInputs.get(thVideoInputs.size() - 1);
        assertThat(testThVideoInput.getVideoInputField()).isEqualTo(DEFAULT_VIDEO_INPUT_FIELD);
        assertThat(testThVideoInput.getVideoInputFieldContentType()).isEqualTo(DEFAULT_VIDEO_INPUT_FIELD_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void checkVideoInputFieldIsRequired() throws Exception {
        int databaseSizeBeforeTest = thVideoInputRepository.findAll().size();
        // set the field null
        thVideoInput.setVideoInputField(null);

        // Create the ThVideoInput, which fails.

        restThVideoInputMockMvc.perform(post("/api/th-video-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(thVideoInput)))
                .andExpect(status().isBadRequest());

        List<ThVideoInput> thVideoInputs = thVideoInputRepository.findAll();
        assertThat(thVideoInputs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThVideoInputs() throws Exception {
        // Initialize the database
        thVideoInputRepository.saveAndFlush(thVideoInput);

        // Get all the thVideoInputs
        restThVideoInputMockMvc.perform(get("/api/th-video-inputs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(thVideoInput.getId().intValue())))
                .andExpect(jsonPath("$.[*].videoInputFieldContentType").value(hasItem(DEFAULT_VIDEO_INPUT_FIELD_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].videoInputField").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO_INPUT_FIELD))));
    }

    @Test
    @Transactional
    public void getThVideoInput() throws Exception {
        // Initialize the database
        thVideoInputRepository.saveAndFlush(thVideoInput);

        // Get the thVideoInput
        restThVideoInputMockMvc.perform(get("/api/th-video-inputs/{id}", thVideoInput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(thVideoInput.getId().intValue()))
            .andExpect(jsonPath("$.videoInputFieldContentType").value(DEFAULT_VIDEO_INPUT_FIELD_CONTENT_TYPE))
            .andExpect(jsonPath("$.videoInputField").value(Base64Utils.encodeToString(DEFAULT_VIDEO_INPUT_FIELD)));
    }

    @Test
    @Transactional
    public void getNonExistingThVideoInput() throws Exception {
        // Get the thVideoInput
        restThVideoInputMockMvc.perform(get("/api/th-video-inputs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThVideoInput() throws Exception {
        // Initialize the database
        thVideoInputRepository.saveAndFlush(thVideoInput);
        int databaseSizeBeforeUpdate = thVideoInputRepository.findAll().size();

        // Update the thVideoInput
        ThVideoInput updatedThVideoInput = new ThVideoInput();
        updatedThVideoInput.setId(thVideoInput.getId());
        updatedThVideoInput.setVideoInputField(UPDATED_VIDEO_INPUT_FIELD);
        updatedThVideoInput.setVideoInputFieldContentType(UPDATED_VIDEO_INPUT_FIELD_CONTENT_TYPE);

        restThVideoInputMockMvc.perform(put("/api/th-video-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedThVideoInput)))
                .andExpect(status().isOk());

        // Validate the ThVideoInput in the database
        List<ThVideoInput> thVideoInputs = thVideoInputRepository.findAll();
        assertThat(thVideoInputs).hasSize(databaseSizeBeforeUpdate);
        ThVideoInput testThVideoInput = thVideoInputs.get(thVideoInputs.size() - 1);
        assertThat(testThVideoInput.getVideoInputField()).isEqualTo(UPDATED_VIDEO_INPUT_FIELD);
        assertThat(testThVideoInput.getVideoInputFieldContentType()).isEqualTo(UPDATED_VIDEO_INPUT_FIELD_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteThVideoInput() throws Exception {
        // Initialize the database
        thVideoInputRepository.saveAndFlush(thVideoInput);
        int databaseSizeBeforeDelete = thVideoInputRepository.findAll().size();

        // Get the thVideoInput
        restThVideoInputMockMvc.perform(delete("/api/th-video-inputs/{id}", thVideoInput.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ThVideoInput> thVideoInputs = thVideoInputRepository.findAll();
        assertThat(thVideoInputs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
