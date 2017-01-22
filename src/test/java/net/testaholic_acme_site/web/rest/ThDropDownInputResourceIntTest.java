package net.testaholic_acme_site.web.rest;

import net.testaholic_acme_site.AcmeSiteApp;
import net.testaholic_acme_site.domain.ThDropDownInput;
import net.testaholic_acme_site.repository.ThDropDownInputRepository;

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
 * Test class for the ThDropDownInputResource REST controller.
 *
 * @see ThDropDownInputResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AcmeSiteApp.class)
@WebAppConfiguration
@IntegrationTest
public class ThDropDownInputResourceIntTest {

    private static final String DEFAULT_DROP_DOWN_INPUT_FIELD = "AAAAA";
    private static final String UPDATED_DROP_DOWN_INPUT_FIELD = "BBBBB";

    @Inject
    private ThDropDownInputRepository thDropDownInputRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restThDropDownInputMockMvc;

    private ThDropDownInput thDropDownInput;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThDropDownInputResource thDropDownInputResource = new ThDropDownInputResource();
        ReflectionTestUtils.setField(thDropDownInputResource, "thDropDownInputRepository", thDropDownInputRepository);
        this.restThDropDownInputMockMvc = MockMvcBuilders.standaloneSetup(thDropDownInputResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        thDropDownInput = new ThDropDownInput();
        thDropDownInput.setDropDownInputField(DEFAULT_DROP_DOWN_INPUT_FIELD);
    }

    @Test
    @Transactional
    public void createThDropDownInput() throws Exception {
        int databaseSizeBeforeCreate = thDropDownInputRepository.findAll().size();

        // Create the ThDropDownInput

        restThDropDownInputMockMvc.perform(post("/api/th-drop-down-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(thDropDownInput)))
                .andExpect(status().isCreated());

        // Validate the ThDropDownInput in the database
        List<ThDropDownInput> thDropDownInputs = thDropDownInputRepository.findAll();
        assertThat(thDropDownInputs).hasSize(databaseSizeBeforeCreate + 1);
        ThDropDownInput testThDropDownInput = thDropDownInputs.get(thDropDownInputs.size() - 1);
        assertThat(testThDropDownInput.getDropDownInputField()).isEqualTo(DEFAULT_DROP_DOWN_INPUT_FIELD);
    }

    @Test
    @Transactional
    public void checkDropDownInputFieldIsRequired() throws Exception {
        int databaseSizeBeforeTest = thDropDownInputRepository.findAll().size();
        // set the field null
        thDropDownInput.setDropDownInputField(null);

        // Create the ThDropDownInput, which fails.

        restThDropDownInputMockMvc.perform(post("/api/th-drop-down-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(thDropDownInput)))
                .andExpect(status().isBadRequest());

        List<ThDropDownInput> thDropDownInputs = thDropDownInputRepository.findAll();
        assertThat(thDropDownInputs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThDropDownInputs() throws Exception {
        // Initialize the database
        thDropDownInputRepository.saveAndFlush(thDropDownInput);

        // Get all the thDropDownInputs
        restThDropDownInputMockMvc.perform(get("/api/th-drop-down-inputs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(thDropDownInput.getId().intValue())))
                .andExpect(jsonPath("$.[*].dropDownInputField").value(hasItem(DEFAULT_DROP_DOWN_INPUT_FIELD.toString())));
    }

    @Test
    @Transactional
    public void getThDropDownInput() throws Exception {
        // Initialize the database
        thDropDownInputRepository.saveAndFlush(thDropDownInput);

        // Get the thDropDownInput
        restThDropDownInputMockMvc.perform(get("/api/th-drop-down-inputs/{id}", thDropDownInput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(thDropDownInput.getId().intValue()))
            .andExpect(jsonPath("$.dropDownInputField").value(DEFAULT_DROP_DOWN_INPUT_FIELD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingThDropDownInput() throws Exception {
        // Get the thDropDownInput
        restThDropDownInputMockMvc.perform(get("/api/th-drop-down-inputs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThDropDownInput() throws Exception {
        // Initialize the database
        thDropDownInputRepository.saveAndFlush(thDropDownInput);
        int databaseSizeBeforeUpdate = thDropDownInputRepository.findAll().size();

        // Update the thDropDownInput
        ThDropDownInput updatedThDropDownInput = new ThDropDownInput();
        updatedThDropDownInput.setId(thDropDownInput.getId());
        updatedThDropDownInput.setDropDownInputField(UPDATED_DROP_DOWN_INPUT_FIELD);

        restThDropDownInputMockMvc.perform(put("/api/th-drop-down-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedThDropDownInput)))
                .andExpect(status().isOk());

        // Validate the ThDropDownInput in the database
        List<ThDropDownInput> thDropDownInputs = thDropDownInputRepository.findAll();
        assertThat(thDropDownInputs).hasSize(databaseSizeBeforeUpdate);
        ThDropDownInput testThDropDownInput = thDropDownInputs.get(thDropDownInputs.size() - 1);
        assertThat(testThDropDownInput.getDropDownInputField()).isEqualTo(UPDATED_DROP_DOWN_INPUT_FIELD);
    }

    @Test
    @Transactional
    public void deleteThDropDownInput() throws Exception {
        // Initialize the database
        thDropDownInputRepository.saveAndFlush(thDropDownInput);
        int databaseSizeBeforeDelete = thDropDownInputRepository.findAll().size();

        // Get the thDropDownInput
        restThDropDownInputMockMvc.perform(delete("/api/th-drop-down-inputs/{id}", thDropDownInput.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ThDropDownInput> thDropDownInputs = thDropDownInputRepository.findAll();
        assertThat(thDropDownInputs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
