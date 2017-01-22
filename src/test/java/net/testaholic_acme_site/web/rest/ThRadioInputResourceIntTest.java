package net.testaholic_acme_site.web.rest;

import net.testaholic_acme_site.AcmeSiteApp;
import net.testaholic_acme_site.domain.ThRadioInput;
import net.testaholic_acme_site.repository.ThRadioInputRepository;

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
 * Test class for the ThRadioInputResource REST controller.
 *
 * @see ThRadioInputResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AcmeSiteApp.class)
@WebAppConfiguration
@IntegrationTest
public class ThRadioInputResourceIntTest {

    private static final String DEFAULT_RADIO_INPUT_FIELD = "AAAAA";
    private static final String UPDATED_RADIO_INPUT_FIELD = "BBBBB";

    @Inject
    private ThRadioInputRepository thRadioInputRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restThRadioInputMockMvc;

    private ThRadioInput thRadioInput;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThRadioInputResource thRadioInputResource = new ThRadioInputResource();
        ReflectionTestUtils.setField(thRadioInputResource, "thRadioInputRepository", thRadioInputRepository);
        this.restThRadioInputMockMvc = MockMvcBuilders.standaloneSetup(thRadioInputResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        thRadioInput = new ThRadioInput();
        thRadioInput.setRadioInputField(DEFAULT_RADIO_INPUT_FIELD);
    }

    @Test
    @Transactional
    public void createThRadioInput() throws Exception {
        int databaseSizeBeforeCreate = thRadioInputRepository.findAll().size();

        // Create the ThRadioInput

        restThRadioInputMockMvc.perform(post("/api/th-radio-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(thRadioInput)))
                .andExpect(status().isCreated());

        // Validate the ThRadioInput in the database
        List<ThRadioInput> thRadioInputs = thRadioInputRepository.findAll();
        assertThat(thRadioInputs).hasSize(databaseSizeBeforeCreate + 1);
        ThRadioInput testThRadioInput = thRadioInputs.get(thRadioInputs.size() - 1);
        assertThat(testThRadioInput.getRadioInputField()).isEqualTo(DEFAULT_RADIO_INPUT_FIELD);
    }

    @Test
    @Transactional
    public void checkRadioInputFieldIsRequired() throws Exception {
        int databaseSizeBeforeTest = thRadioInputRepository.findAll().size();
        // set the field null
        thRadioInput.setRadioInputField(null);

        // Create the ThRadioInput, which fails.

        restThRadioInputMockMvc.perform(post("/api/th-radio-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(thRadioInput)))
                .andExpect(status().isBadRequest());

        List<ThRadioInput> thRadioInputs = thRadioInputRepository.findAll();
        assertThat(thRadioInputs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThRadioInputs() throws Exception {
        // Initialize the database
        thRadioInputRepository.saveAndFlush(thRadioInput);

        // Get all the thRadioInputs
        restThRadioInputMockMvc.perform(get("/api/th-radio-inputs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(thRadioInput.getId().intValue())))
                .andExpect(jsonPath("$.[*].radioInputField").value(hasItem(DEFAULT_RADIO_INPUT_FIELD.toString())));
    }

    @Test
    @Transactional
    public void getThRadioInput() throws Exception {
        // Initialize the database
        thRadioInputRepository.saveAndFlush(thRadioInput);

        // Get the thRadioInput
        restThRadioInputMockMvc.perform(get("/api/th-radio-inputs/{id}", thRadioInput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(thRadioInput.getId().intValue()))
            .andExpect(jsonPath("$.radioInputField").value(DEFAULT_RADIO_INPUT_FIELD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingThRadioInput() throws Exception {
        // Get the thRadioInput
        restThRadioInputMockMvc.perform(get("/api/th-radio-inputs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThRadioInput() throws Exception {
        // Initialize the database
        thRadioInputRepository.saveAndFlush(thRadioInput);
        int databaseSizeBeforeUpdate = thRadioInputRepository.findAll().size();

        // Update the thRadioInput
        ThRadioInput updatedThRadioInput = new ThRadioInput();
        updatedThRadioInput.setId(thRadioInput.getId());
        updatedThRadioInput.setRadioInputField(UPDATED_RADIO_INPUT_FIELD);

        restThRadioInputMockMvc.perform(put("/api/th-radio-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedThRadioInput)))
                .andExpect(status().isOk());

        // Validate the ThRadioInput in the database
        List<ThRadioInput> thRadioInputs = thRadioInputRepository.findAll();
        assertThat(thRadioInputs).hasSize(databaseSizeBeforeUpdate);
        ThRadioInput testThRadioInput = thRadioInputs.get(thRadioInputs.size() - 1);
        assertThat(testThRadioInput.getRadioInputField()).isEqualTo(UPDATED_RADIO_INPUT_FIELD);
    }

    @Test
    @Transactional
    public void deleteThRadioInput() throws Exception {
        // Initialize the database
        thRadioInputRepository.saveAndFlush(thRadioInput);
        int databaseSizeBeforeDelete = thRadioInputRepository.findAll().size();

        // Get the thRadioInput
        restThRadioInputMockMvc.perform(delete("/api/th-radio-inputs/{id}", thRadioInput.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ThRadioInput> thRadioInputs = thRadioInputRepository.findAll();
        assertThat(thRadioInputs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
