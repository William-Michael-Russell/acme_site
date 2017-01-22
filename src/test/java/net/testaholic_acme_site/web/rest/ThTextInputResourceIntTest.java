package net.testaholic_acme_site.web.rest;

import net.testaholic_acme_site.AcmeSiteApp;
import net.testaholic_acme_site.domain.ThTextInput;
import net.testaholic_acme_site.repository.ThTextInputRepository;

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
 * Test class for the ThTextInputResource REST controller.
 *
 * @see ThTextInputResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AcmeSiteApp.class)
@WebAppConfiguration
@IntegrationTest
public class ThTextInputResourceIntTest {

    private static final String DEFAULT_TEXTED_INPUT_FIELD = "A";
    private static final String UPDATED_TEXTED_INPUT_FIELD = "B";

    @Inject
    private ThTextInputRepository thTextInputRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restThTextInputMockMvc;

    private ThTextInput thTextInput;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThTextInputResource thTextInputResource = new ThTextInputResource();
        ReflectionTestUtils.setField(thTextInputResource, "thTextInputRepository", thTextInputRepository);
        this.restThTextInputMockMvc = MockMvcBuilders.standaloneSetup(thTextInputResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        thTextInput = new ThTextInput();
        thTextInput.setTextedInputField(DEFAULT_TEXTED_INPUT_FIELD);
    }

    @Test
    @Transactional
    public void createThTextInput() throws Exception {
        int databaseSizeBeforeCreate = thTextInputRepository.findAll().size();

        // Create the ThTextInput

        restThTextInputMockMvc.perform(post("/api/th-text-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(thTextInput)))
                .andExpect(status().isCreated());

        // Validate the ThTextInput in the database
        List<ThTextInput> thTextInputs = thTextInputRepository.findAll();
        assertThat(thTextInputs).hasSize(databaseSizeBeforeCreate + 1);
        ThTextInput testThTextInput = thTextInputs.get(thTextInputs.size() - 1);
        assertThat(testThTextInput.getTextedInputField()).isEqualTo(DEFAULT_TEXTED_INPUT_FIELD);
    }

    @Test
    @Transactional
    public void getAllThTextInputs() throws Exception {
        // Initialize the database
        thTextInputRepository.saveAndFlush(thTextInput);

        // Get all the thTextInputs
        restThTextInputMockMvc.perform(get("/api/th-text-inputs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(thTextInput.getId().intValue())))
                .andExpect(jsonPath("$.[*].textedInputField").value(hasItem(DEFAULT_TEXTED_INPUT_FIELD.toString())));
    }

    @Test
    @Transactional
    public void getThTextInput() throws Exception {
        // Initialize the database
        thTextInputRepository.saveAndFlush(thTextInput);

        // Get the thTextInput
        restThTextInputMockMvc.perform(get("/api/th-text-inputs/{id}", thTextInput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(thTextInput.getId().intValue()))
            .andExpect(jsonPath("$.textedInputField").value(DEFAULT_TEXTED_INPUT_FIELD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingThTextInput() throws Exception {
        // Get the thTextInput
        restThTextInputMockMvc.perform(get("/api/th-text-inputs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThTextInput() throws Exception {
        // Initialize the database
        thTextInputRepository.saveAndFlush(thTextInput);
        int databaseSizeBeforeUpdate = thTextInputRepository.findAll().size();

        // Update the thTextInput
        ThTextInput updatedThTextInput = new ThTextInput();
        updatedThTextInput.setId(thTextInput.getId());
        updatedThTextInput.setTextedInputField(UPDATED_TEXTED_INPUT_FIELD);

        restThTextInputMockMvc.perform(put("/api/th-text-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedThTextInput)))
                .andExpect(status().isOk());

        // Validate the ThTextInput in the database
        List<ThTextInput> thTextInputs = thTextInputRepository.findAll();
        assertThat(thTextInputs).hasSize(databaseSizeBeforeUpdate);
        ThTextInput testThTextInput = thTextInputs.get(thTextInputs.size() - 1);
        assertThat(testThTextInput.getTextedInputField()).isEqualTo(UPDATED_TEXTED_INPUT_FIELD);
    }

    @Test
    @Transactional
    public void deleteThTextInput() throws Exception {
        // Initialize the database
        thTextInputRepository.saveAndFlush(thTextInput);
        int databaseSizeBeforeDelete = thTextInputRepository.findAll().size();

        // Get the thTextInput
        restThTextInputMockMvc.perform(delete("/api/th-text-inputs/{id}", thTextInput.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ThTextInput> thTextInputs = thTextInputRepository.findAll();
        assertThat(thTextInputs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
