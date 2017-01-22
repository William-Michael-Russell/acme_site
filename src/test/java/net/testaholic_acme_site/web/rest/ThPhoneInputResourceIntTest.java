package net.testaholic_acme_site.web.rest;

import net.testaholic_acme_site.AcmeSiteApp;
import net.testaholic_acme_site.domain.ThPhoneInput;
import net.testaholic_acme_site.repository.ThPhoneInputRepository;

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
 * Test class for the ThPhoneInputResource REST controller.
 *
 * @see ThPhoneInputResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AcmeSiteApp.class)
@WebAppConfiguration
@IntegrationTest
public class ThPhoneInputResourceIntTest {

    private static final String DEFAULT_PHONE_INPUT_FIELD = "AAAAA";
    private static final String UPDATED_PHONE_INPUT_FIELD = "BBBBB";

    @Inject
    private ThPhoneInputRepository thPhoneInputRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restThPhoneInputMockMvc;

    private ThPhoneInput thPhoneInput;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThPhoneInputResource thPhoneInputResource = new ThPhoneInputResource();
        ReflectionTestUtils.setField(thPhoneInputResource, "thPhoneInputRepository", thPhoneInputRepository);
        this.restThPhoneInputMockMvc = MockMvcBuilders.standaloneSetup(thPhoneInputResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        thPhoneInput = new ThPhoneInput();
        thPhoneInput.setPhoneInputField(DEFAULT_PHONE_INPUT_FIELD);
    }

    @Test
    @Transactional
    public void createThPhoneInput() throws Exception {
        int databaseSizeBeforeCreate = thPhoneInputRepository.findAll().size();

        // Create the ThPhoneInput

        restThPhoneInputMockMvc.perform(post("/api/th-phone-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(thPhoneInput)))
                .andExpect(status().isCreated());

        // Validate the ThPhoneInput in the database
        List<ThPhoneInput> thPhoneInputs = thPhoneInputRepository.findAll();
        assertThat(thPhoneInputs).hasSize(databaseSizeBeforeCreate + 1);
        ThPhoneInput testThPhoneInput = thPhoneInputs.get(thPhoneInputs.size() - 1);
        assertThat(testThPhoneInput.getPhoneInputField()).isEqualTo(DEFAULT_PHONE_INPUT_FIELD);
    }

    @Test
    @Transactional
    public void getAllThPhoneInputs() throws Exception {
        // Initialize the database
        thPhoneInputRepository.saveAndFlush(thPhoneInput);

        // Get all the thPhoneInputs
        restThPhoneInputMockMvc.perform(get("/api/th-phone-inputs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(thPhoneInput.getId().intValue())))
                .andExpect(jsonPath("$.[*].phoneInputField").value(hasItem(DEFAULT_PHONE_INPUT_FIELD.toString())));
    }

    @Test
    @Transactional
    public void getThPhoneInput() throws Exception {
        // Initialize the database
        thPhoneInputRepository.saveAndFlush(thPhoneInput);

        // Get the thPhoneInput
        restThPhoneInputMockMvc.perform(get("/api/th-phone-inputs/{id}", thPhoneInput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(thPhoneInput.getId().intValue()))
            .andExpect(jsonPath("$.phoneInputField").value(DEFAULT_PHONE_INPUT_FIELD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingThPhoneInput() throws Exception {
        // Get the thPhoneInput
        restThPhoneInputMockMvc.perform(get("/api/th-phone-inputs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThPhoneInput() throws Exception {
        // Initialize the database
        thPhoneInputRepository.saveAndFlush(thPhoneInput);
        int databaseSizeBeforeUpdate = thPhoneInputRepository.findAll().size();

        // Update the thPhoneInput
        ThPhoneInput updatedThPhoneInput = new ThPhoneInput();
        updatedThPhoneInput.setId(thPhoneInput.getId());
        updatedThPhoneInput.setPhoneInputField(UPDATED_PHONE_INPUT_FIELD);

        restThPhoneInputMockMvc.perform(put("/api/th-phone-inputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedThPhoneInput)))
                .andExpect(status().isOk());

        // Validate the ThPhoneInput in the database
        List<ThPhoneInput> thPhoneInputs = thPhoneInputRepository.findAll();
        assertThat(thPhoneInputs).hasSize(databaseSizeBeforeUpdate);
        ThPhoneInput testThPhoneInput = thPhoneInputs.get(thPhoneInputs.size() - 1);
        assertThat(testThPhoneInput.getPhoneInputField()).isEqualTo(UPDATED_PHONE_INPUT_FIELD);
    }

    @Test
    @Transactional
    public void deleteThPhoneInput() throws Exception {
        // Initialize the database
        thPhoneInputRepository.saveAndFlush(thPhoneInput);
        int databaseSizeBeforeDelete = thPhoneInputRepository.findAll().size();

        // Get the thPhoneInput
        restThPhoneInputMockMvc.perform(delete("/api/th-phone-inputs/{id}", thPhoneInput.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ThPhoneInput> thPhoneInputs = thPhoneInputRepository.findAll();
        assertThat(thPhoneInputs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
