package net.testaholic_acme_site.web.rest;

import net.testaholic_acme_site.AcmeSiteApp;
import net.testaholic_acme_site.domain.ThAlertPrompts;
import net.testaholic_acme_site.repository.ThAlertPromptsRepository;

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
 * Test class for the ThAlertPromptsResource REST controller.
 *
 * @see ThAlertPromptsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AcmeSiteApp.class)
@WebAppConfiguration
@IntegrationTest
public class ThAlertPromptsResourceIntTest {


    @Inject
    private ThAlertPromptsRepository thAlertPromptsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restThAlertPromptsMockMvc;

    private ThAlertPrompts thAlertPrompts;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThAlertPromptsResource thAlertPromptsResource = new ThAlertPromptsResource();
        ReflectionTestUtils.setField(thAlertPromptsResource, "thAlertPromptsRepository", thAlertPromptsRepository);
        this.restThAlertPromptsMockMvc = MockMvcBuilders.standaloneSetup(thAlertPromptsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        thAlertPrompts = new ThAlertPrompts();
    }

    @Test
    @Transactional
    public void createThAlertPrompts() throws Exception {
        int databaseSizeBeforeCreate = thAlertPromptsRepository.findAll().size();

        // Create the ThAlertPrompts

        restThAlertPromptsMockMvc.perform(post("/api/th-alert-prompts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(thAlertPrompts)))
                .andExpect(status().isCreated());

        // Validate the ThAlertPrompts in the database
        List<ThAlertPrompts> thAlertPrompts = thAlertPromptsRepository.findAll();
        assertThat(thAlertPrompts).hasSize(databaseSizeBeforeCreate + 1);
        ThAlertPrompts testThAlertPrompts = thAlertPrompts.get(thAlertPrompts.size() - 1);
    }

    @Test
    @Transactional
    public void getAllThAlertPrompts() throws Exception {
        // Initialize the database
        thAlertPromptsRepository.saveAndFlush(thAlertPrompts);

        // Get all the thAlertPrompts
        restThAlertPromptsMockMvc.perform(get("/api/th-alert-prompts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(thAlertPrompts.getId().intValue())));
    }

    @Test
    @Transactional
    public void getThAlertPrompts() throws Exception {
        // Initialize the database
        thAlertPromptsRepository.saveAndFlush(thAlertPrompts);

        // Get the thAlertPrompts
        restThAlertPromptsMockMvc.perform(get("/api/th-alert-prompts/{id}", thAlertPrompts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(thAlertPrompts.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingThAlertPrompts() throws Exception {
        // Get the thAlertPrompts
        restThAlertPromptsMockMvc.perform(get("/api/th-alert-prompts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThAlertPrompts() throws Exception {
        // Initialize the database
        thAlertPromptsRepository.saveAndFlush(thAlertPrompts);
        int databaseSizeBeforeUpdate = thAlertPromptsRepository.findAll().size();

        // Update the thAlertPrompts
        ThAlertPrompts updatedThAlertPrompts = new ThAlertPrompts();
        updatedThAlertPrompts.setId(thAlertPrompts.getId());

        restThAlertPromptsMockMvc.perform(put("/api/th-alert-prompts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedThAlertPrompts)))
                .andExpect(status().isOk());

        // Validate the ThAlertPrompts in the database
        List<ThAlertPrompts> thAlertPrompts = thAlertPromptsRepository.findAll();
        assertThat(thAlertPrompts).hasSize(databaseSizeBeforeUpdate);
        ThAlertPrompts testThAlertPrompts = thAlertPrompts.get(thAlertPrompts.size() - 1);
    }

    @Test
    @Transactional
    public void deleteThAlertPrompts() throws Exception {
        // Initialize the database
        thAlertPromptsRepository.saveAndFlush(thAlertPrompts);
        int databaseSizeBeforeDelete = thAlertPromptsRepository.findAll().size();

        // Get the thAlertPrompts
        restThAlertPromptsMockMvc.perform(delete("/api/th-alert-prompts/{id}", thAlertPrompts.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ThAlertPrompts> thAlertPrompts = thAlertPromptsRepository.findAll();
        assertThat(thAlertPrompts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
