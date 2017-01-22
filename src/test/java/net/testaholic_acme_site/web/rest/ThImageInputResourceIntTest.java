package net.testaholic_acme_site.web.rest;

import net.testaholic_acme_site.AcmeSiteApp;
import net.testaholic_acme_site.domain.ThImageInput;
import net.testaholic_acme_site.repository.ThImageInputRepository;

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
 * Test class for the ThImageInputResource REST controller.
 *
 * @see ThImageInputResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AcmeSiteApp.class)
@WebAppConfiguration
@IntegrationTest
public class ThImageInputResourceIntTest {


    private static final byte[] DEFAULT_IMAGE_INPUT_FIELD = TestUtil.createByteArray(5, "0");
    private static final byte[] UPDATED_IMAGE_INPUT_FIELD = TestUtil.createByteArray(5000000, "1");
    private static final String DEFAULT_IMAGE_INPUT_FIELD_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_INPUT_FIELD_CONTENT_TYPE = "image/png";

    @Inject
    private ThImageInputRepository thImageInputRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restThImageInputMockMvc;

    private ThImageInput thImageInput;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThImageInputResource thImageInputResource = new ThImageInputResource();
        ReflectionTestUtils.setField(thImageInputResource, "thImageInputRepository", thImageInputRepository);
        this.restThImageInputMockMvc = MockMvcBuilders.standaloneSetup(thImageInputResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        thImageInput = new ThImageInput();
        thImageInput.setImageInputField(DEFAULT_IMAGE_INPUT_FIELD);
        thImageInput.setImageInputFieldContentType(DEFAULT_IMAGE_INPUT_FIELD_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createThImageInput() throws Exception {
        int databaseSizeBeforeCreate = thImageInputRepository.findAll().size();

        // Create the ThImageInput

        restThImageInputMockMvc.perform(post("/api/th-image-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(thImageInput)))
                .andExpect(status().isCreated());

        // Validate the ThImageInput in the database
        List<ThImageInput> thImageInputs = thImageInputRepository.findAll();
        assertThat(thImageInputs).hasSize(databaseSizeBeforeCreate + 1);
        ThImageInput testThImageInput = thImageInputs.get(thImageInputs.size() - 1);
        assertThat(testThImageInput.getImageInputField()).isEqualTo(DEFAULT_IMAGE_INPUT_FIELD);
        assertThat(testThImageInput.getImageInputFieldContentType()).isEqualTo(DEFAULT_IMAGE_INPUT_FIELD_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void checkImageInputFieldIsRequired() throws Exception {
        int databaseSizeBeforeTest = thImageInputRepository.findAll().size();
        // set the field null
        thImageInput.setImageInputField(null);

        // Create the ThImageInput, which fails.

        restThImageInputMockMvc.perform(post("/api/th-image-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(thImageInput)))
                .andExpect(status().isBadRequest());

        List<ThImageInput> thImageInputs = thImageInputRepository.findAll();
        assertThat(thImageInputs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThImageInputs() throws Exception {
        // Initialize the database
        thImageInputRepository.saveAndFlush(thImageInput);

        // Get all the thImageInputs
        restThImageInputMockMvc.perform(get("/api/th-image-inputs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(thImageInput.getId().intValue())))
                .andExpect(jsonPath("$.[*].imageInputFieldContentType").value(hasItem(DEFAULT_IMAGE_INPUT_FIELD_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].imageInputField").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_INPUT_FIELD))));
    }

    @Test
    @Transactional
    public void getThImageInput() throws Exception {
        // Initialize the database
        thImageInputRepository.saveAndFlush(thImageInput);

        // Get the thImageInput
        restThImageInputMockMvc.perform(get("/api/th-image-inputs/{id}", thImageInput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(thImageInput.getId().intValue()))
            .andExpect(jsonPath("$.imageInputFieldContentType").value(DEFAULT_IMAGE_INPUT_FIELD_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageInputField").value(Base64Utils.encodeToString(DEFAULT_IMAGE_INPUT_FIELD)));
    }

    @Test
    @Transactional
    public void getNonExistingThImageInput() throws Exception {
        // Get the thImageInput
        restThImageInputMockMvc.perform(get("/api/th-image-inputs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThImageInput() throws Exception {
        // Initialize the database
        thImageInputRepository.saveAndFlush(thImageInput);
        int databaseSizeBeforeUpdate = thImageInputRepository.findAll().size();

        // Update the thImageInput
        ThImageInput updatedThImageInput = new ThImageInput();
        updatedThImageInput.setId(thImageInput.getId());
        updatedThImageInput.setImageInputField(UPDATED_IMAGE_INPUT_FIELD);
        updatedThImageInput.setImageInputFieldContentType(UPDATED_IMAGE_INPUT_FIELD_CONTENT_TYPE);

        restThImageInputMockMvc.perform(put("/api/th-image-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedThImageInput)))
                .andExpect(status().isOk());

        // Validate the ThImageInput in the database
        List<ThImageInput> thImageInputs = thImageInputRepository.findAll();
        assertThat(thImageInputs).hasSize(databaseSizeBeforeUpdate);
        ThImageInput testThImageInput = thImageInputs.get(thImageInputs.size() - 1);
        assertThat(testThImageInput.getImageInputField()).isEqualTo(UPDATED_IMAGE_INPUT_FIELD);
        assertThat(testThImageInput.getImageInputFieldContentType()).isEqualTo(UPDATED_IMAGE_INPUT_FIELD_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteThImageInput() throws Exception {
        // Initialize the database
        thImageInputRepository.saveAndFlush(thImageInput);
        int databaseSizeBeforeDelete = thImageInputRepository.findAll().size();

        // Get the thImageInput
        restThImageInputMockMvc.perform(delete("/api/th-image-inputs/{id}", thImageInput.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ThImageInput> thImageInputs = thImageInputRepository.findAll();
        assertThat(thImageInputs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
