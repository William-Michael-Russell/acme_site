package net.testaholic_acme_site.web.rest;

import net.testaholic_acme_site.AcmeSiteApp;
import net.testaholic_acme_site.domain.ThPasswordInput;
import net.testaholic_acme_site.repository.ThPasswordInputRepository;

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
 * Test class for the ThPasswordInputResource REST controller.
 *
 * @see ThPasswordInputResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AcmeSiteApp.class)
@WebAppConfiguration
@IntegrationTest
public class ThPasswordInputResourceIntTest {

    private static final String DEFAULT_PASSWORD_INPUT_FIELD = "";
    private static final String UPDATED_PASSWORD_INPUT_FIELD = "";

    @Inject
    private ThPasswordInputRepository thPasswordInputRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restThPasswordInputMockMvc;

    private ThPasswordInput thPasswordInput;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThPasswordInputResource thPasswordInputResource = new ThPasswordInputResource();
        ReflectionTestUtils.setField(thPasswordInputResource, "thPasswordInputRepository", thPasswordInputRepository);
        this.restThPasswordInputMockMvc = MockMvcBuilders.standaloneSetup(thPasswordInputResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        thPasswordInput = new ThPasswordInput();
        thPasswordInput.setPasswordInputField(DEFAULT_PASSWORD_INPUT_FIELD);
    }

    @Test
    @Transactional
    public void createThPasswordInput() throws Exception {
        int databaseSizeBeforeCreate = thPasswordInputRepository.findAll().size();

        // Create the ThPasswordInput

        restThPasswordInputMockMvc.perform(post("/api/th-password-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(thPasswordInput)))
                .andExpect(status().isCreated());

        // Validate the ThPasswordInput in the database
        List<ThPasswordInput> thPasswordInputs = thPasswordInputRepository.findAll();
        assertThat(thPasswordInputs).hasSize(databaseSizeBeforeCreate + 1);
        ThPasswordInput testThPasswordInput = thPasswordInputs.get(thPasswordInputs.size() - 1);
        assertThat(testThPasswordInput.getPasswordInputField()).isEqualTo(DEFAULT_PASSWORD_INPUT_FIELD);
    }

    @Test
    @Transactional
    public void checkPasswordInputFieldIsRequired() throws Exception {
        int databaseSizeBeforeTest = thPasswordInputRepository.findAll().size();
        // set the field null
        thPasswordInput.setPasswordInputField(null);

        // Create the ThPasswordInput, which fails.

        restThPasswordInputMockMvc.perform(post("/api/th-password-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(thPasswordInput)))
                .andExpect(status().isBadRequest());

        List<ThPasswordInput> thPasswordInputs = thPasswordInputRepository.findAll();
        assertThat(thPasswordInputs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThPasswordInputs() throws Exception {
        // Initialize the database
        thPasswordInputRepository.saveAndFlush(thPasswordInput);

        // Get all the thPasswordInputs
        restThPasswordInputMockMvc.perform(get("/api/th-password-inputs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(thPasswordInput.getId().intValue())))
                .andExpect(jsonPath("$.[*].passwordInputField").value(hasItem(DEFAULT_PASSWORD_INPUT_FIELD.toString())));
    }

    @Test
    @Transactional
    public void getThPasswordInput() throws Exception {
        // Initialize the database
        thPasswordInputRepository.saveAndFlush(thPasswordInput);

        // Get the thPasswordInput
        restThPasswordInputMockMvc.perform(get("/api/th-password-inputs/{id}", thPasswordInput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(thPasswordInput.getId().intValue()))
            .andExpect(jsonPath("$.passwordInputField").value(DEFAULT_PASSWORD_INPUT_FIELD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingThPasswordInput() throws Exception {
        // Get the thPasswordInput
        restThPasswordInputMockMvc.perform(get("/api/th-password-inputs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThPasswordInput() throws Exception {
        // Initialize the database
        thPasswordInputRepository.saveAndFlush(thPasswordInput);
        int databaseSizeBeforeUpdate = thPasswordInputRepository.findAll().size();

        // Update the thPasswordInput
        ThPasswordInput updatedThPasswordInput = new ThPasswordInput();
        updatedThPasswordInput.setId(thPasswordInput.getId());
        updatedThPasswordInput.setPasswordInputField(UPDATED_PASSWORD_INPUT_FIELD);

        restThPasswordInputMockMvc.perform(put("/api/th-password-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedThPasswordInput)))
                .andExpect(status().isOk());

        // Validate the ThPasswordInput in the database
        List<ThPasswordInput> thPasswordInputs = thPasswordInputRepository.findAll();
        assertThat(thPasswordInputs).hasSize(databaseSizeBeforeUpdate);
        ThPasswordInput testThPasswordInput = thPasswordInputs.get(thPasswordInputs.size() - 1);
        assertThat(testThPasswordInput.getPasswordInputField()).isEqualTo(UPDATED_PASSWORD_INPUT_FIELD);
    }

    @Test
    @Transactional
    public void deleteThPasswordInput() throws Exception {
        // Initialize the database
        thPasswordInputRepository.saveAndFlush(thPasswordInput);
        int databaseSizeBeforeDelete = thPasswordInputRepository.findAll().size();

        // Get the thPasswordInput
        restThPasswordInputMockMvc.perform(delete("/api/th-password-inputs/{id}", thPasswordInput.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ThPasswordInput> thPasswordInputs = thPasswordInputRepository.findAll();
        assertThat(thPasswordInputs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
