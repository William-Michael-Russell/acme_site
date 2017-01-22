package net.testaholic_acme_site.web.rest;

import net.testaholic_acme_site.AcmeSiteApp;
import net.testaholic_acme_site.domain.ThEmailInput;
import net.testaholic_acme_site.repository.ThEmailInputRepository;

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
 * Test class for the ThEmailInputResource REST controller.
 *
 * @see ThEmailInputResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AcmeSiteApp.class)
@WebAppConfiguration
@IntegrationTest
public class ThEmailInputResourceIntTest {

    private static final String DEFAULT_EMAIL_INPUT_FIELD = "AAAAA";
    private static final String UPDATED_EMAIL_INPUT_FIELD = "BBBBB";

    @Inject
    private ThEmailInputRepository thEmailInputRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restThEmailInputMockMvc;

    private ThEmailInput thEmailInput;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThEmailInputResource thEmailInputResource = new ThEmailInputResource();
        ReflectionTestUtils.setField(thEmailInputResource, "thEmailInputRepository", thEmailInputRepository);
        this.restThEmailInputMockMvc = MockMvcBuilders.standaloneSetup(thEmailInputResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        thEmailInput = new ThEmailInput();
        thEmailInput.setEmailInputField(DEFAULT_EMAIL_INPUT_FIELD);
    }

    @Test
    @Transactional
    public void createThEmailInput() throws Exception {
        int databaseSizeBeforeCreate = thEmailInputRepository.findAll().size();

        // Create the ThEmailInput

        restThEmailInputMockMvc.perform(post("/api/th-email-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(thEmailInput)))
                .andExpect(status().isCreated());

        // Validate the ThEmailInput in the database
        List<ThEmailInput> thEmailInputs = thEmailInputRepository.findAll();
        assertThat(thEmailInputs).hasSize(databaseSizeBeforeCreate + 1);
        ThEmailInput testThEmailInput = thEmailInputs.get(thEmailInputs.size() - 1);
        assertThat(testThEmailInput.getEmailInputField()).isEqualTo(DEFAULT_EMAIL_INPUT_FIELD);
    }

    @Test
    @Transactional
    public void getAllThEmailInputs() throws Exception {
        // Initialize the database
        thEmailInputRepository.saveAndFlush(thEmailInput);

        // Get all the thEmailInputs
        restThEmailInputMockMvc.perform(get("/api/th-email-inputs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(thEmailInput.getId().intValue())))
                .andExpect(jsonPath("$.[*].emailInputField").value(hasItem(DEFAULT_EMAIL_INPUT_FIELD.toString())));
    }

    @Test
    @Transactional
    public void getThEmailInput() throws Exception {
        // Initialize the database
        thEmailInputRepository.saveAndFlush(thEmailInput);

        // Get the thEmailInput
        restThEmailInputMockMvc.perform(get("/api/th-email-inputs/{id}", thEmailInput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(thEmailInput.getId().intValue()))
            .andExpect(jsonPath("$.emailInputField").value(DEFAULT_EMAIL_INPUT_FIELD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingThEmailInput() throws Exception {
        // Get the thEmailInput
        restThEmailInputMockMvc.perform(get("/api/th-email-inputs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThEmailInput() throws Exception {
        // Initialize the database
        thEmailInputRepository.saveAndFlush(thEmailInput);
        int databaseSizeBeforeUpdate = thEmailInputRepository.findAll().size();

        // Update the thEmailInput
        ThEmailInput updatedThEmailInput = new ThEmailInput();
        updatedThEmailInput.setId(thEmailInput.getId());
        updatedThEmailInput.setEmailInputField(UPDATED_EMAIL_INPUT_FIELD);

        restThEmailInputMockMvc.perform(put("/api/th-email-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedThEmailInput)))
                .andExpect(status().isOk());

        // Validate the ThEmailInput in the database
        List<ThEmailInput> thEmailInputs = thEmailInputRepository.findAll();
        assertThat(thEmailInputs).hasSize(databaseSizeBeforeUpdate);
        ThEmailInput testThEmailInput = thEmailInputs.get(thEmailInputs.size() - 1);
        assertThat(testThEmailInput.getEmailInputField()).isEqualTo(UPDATED_EMAIL_INPUT_FIELD);
    }

    @Test
    @Transactional
    public void deleteThEmailInput() throws Exception {
        // Initialize the database
        thEmailInputRepository.saveAndFlush(thEmailInput);
        int databaseSizeBeforeDelete = thEmailInputRepository.findAll().size();

        // Get the thEmailInput
        restThEmailInputMockMvc.perform(delete("/api/th-email-inputs/{id}", thEmailInput.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ThEmailInput> thEmailInputs = thEmailInputRepository.findAll();
        assertThat(thEmailInputs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
