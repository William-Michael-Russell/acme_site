package net.testaholic_acme_site.web.rest;

import net.testaholic_acme_site.AcmeSiteApp;
import net.testaholic_acme_site.domain.ThNumericInput;
import net.testaholic_acme_site.repository.ThNumericInputRepository;

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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ThNumericInputResource REST controller.
 *
 * @see ThNumericInputResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AcmeSiteApp.class)
@WebAppConfiguration
@IntegrationTest
public class ThNumericInputResourceIntTest {


    private static final Integer DEFAULT_NUMERIC_INPUT_FIELD = 1;
    private static final Integer UPDATED_NUMERIC_INPUT_FIELD = 2;

    @Inject
    private ThNumericInputRepository thNumericInputRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restThNumericInputMockMvc;

    private ThNumericInput thNumericInput;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThNumericInputResource thNumericInputResource = new ThNumericInputResource();
        ReflectionTestUtils.setField(thNumericInputResource, "thNumericInputRepository", thNumericInputRepository);
        this.restThNumericInputMockMvc = MockMvcBuilders.standaloneSetup(thNumericInputResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        thNumericInput = new ThNumericInput();
        thNumericInput.setNumericInputField(DEFAULT_NUMERIC_INPUT_FIELD);
    }

    @Test
    @Transactional
    public void createThNumericInput() throws Exception {
        int databaseSizeBeforeCreate = thNumericInputRepository.findAll().size();

        // Create the ThNumericInput

        restThNumericInputMockMvc.perform(post("/api/th-numeric-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(thNumericInput)))
                .andExpect(status().isCreated());

        // Validate the ThNumericInput in the database
        List<ThNumericInput> thNumericInputs = thNumericInputRepository.findAll();
        assertThat(thNumericInputs).hasSize(databaseSizeBeforeCreate + 1);
        ThNumericInput testThNumericInput = thNumericInputs.get(thNumericInputs.size() - 1);
        assertThat(testThNumericInput.getNumericInputField()).isEqualTo(DEFAULT_NUMERIC_INPUT_FIELD);
    }

    @Test
    @Transactional
    public void getAllThNumericInputs() throws Exception {
        // Initialize the database
        thNumericInputRepository.saveAndFlush(thNumericInput);

        // Get all the thNumericInputs
        restThNumericInputMockMvc.perform(get("/api/th-numeric-inputs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(thNumericInput.getId().intValue())))
                .andExpect(jsonPath("$.[*].numericInputField").value(hasItem(DEFAULT_NUMERIC_INPUT_FIELD)));
    }

    @Test
    @Transactional
    public void getThNumericInput() throws Exception {
        // Initialize the database
        thNumericInputRepository.saveAndFlush(thNumericInput);

        // Get the thNumericInput
        restThNumericInputMockMvc.perform(get("/api/th-numeric-inputs/{id}", thNumericInput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(thNumericInput.getId().intValue()))
            .andExpect(jsonPath("$.numericInputField").value(DEFAULT_NUMERIC_INPUT_FIELD));
    }

    @Test
    @Transactional
    public void getNonExistingThNumericInput() throws Exception {
        // Get the thNumericInput
        restThNumericInputMockMvc.perform(get("/api/th-numeric-inputs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThNumericInput() throws Exception {
        // Initialize the database
        thNumericInputRepository.saveAndFlush(thNumericInput);
        int databaseSizeBeforeUpdate = thNumericInputRepository.findAll().size();

        // Update the thNumericInput
        ThNumericInput updatedThNumericInput = new ThNumericInput();
        updatedThNumericInput.setId(thNumericInput.getId());
        updatedThNumericInput.setNumericInputField(UPDATED_NUMERIC_INPUT_FIELD);

        restThNumericInputMockMvc.perform(put("/api/th-numeric-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedThNumericInput)))
                .andExpect(status().isOk());

        // Validate the ThNumericInput in the database
        List<ThNumericInput> thNumericInputs = thNumericInputRepository.findAll();
        assertThat(thNumericInputs).hasSize(databaseSizeBeforeUpdate);
        ThNumericInput testThNumericInput = thNumericInputs.get(thNumericInputs.size() - 1);
        assertThat(testThNumericInput.getNumericInputField()).isEqualTo(UPDATED_NUMERIC_INPUT_FIELD);
    }

    @Test
    @Transactional
    public void deleteThNumericInput() throws Exception {
        // Initialize the database
        thNumericInputRepository.saveAndFlush(thNumericInput);
        int databaseSizeBeforeDelete = thNumericInputRepository.findAll().size();

        // Get the thNumericInput
        restThNumericInputMockMvc.perform(delete("/api/th-numeric-inputs/{id}", thNumericInput.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ThNumericInput> thNumericInputs = thNumericInputRepository.findAll();
        assertThat(thNumericInputs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
