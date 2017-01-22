package net.testaholic_acme_site.web.rest;

import net.testaholic_acme_site.AcmeSiteApp;
import net.testaholic_acme_site.domain.ThSelectorPaths;
import net.testaholic_acme_site.repository.ThSelectorPathsRepository;

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
 * Test class for the ThSelectorPathsResource REST controller.
 *
 * @see ThSelectorPathsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AcmeSiteApp.class)
@WebAppConfiguration
@IntegrationTest
public class ThSelectorPathsResourceIntTest {


    @Inject
    private ThSelectorPathsRepository thSelectorPathsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restThSelectorPathsMockMvc;

    private ThSelectorPaths thSelectorPaths;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThSelectorPathsResource thSelectorPathsResource = new ThSelectorPathsResource();
        ReflectionTestUtils.setField(thSelectorPathsResource, "thSelectorPathsRepository", thSelectorPathsRepository);
        this.restThSelectorPathsMockMvc = MockMvcBuilders.standaloneSetup(thSelectorPathsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        thSelectorPaths = new ThSelectorPaths();
    }

    @Test
    @Transactional
    public void createThSelectorPaths() throws Exception {
        int databaseSizeBeforeCreate = thSelectorPathsRepository.findAll().size();

        // Create the ThSelectorPaths

        restThSelectorPathsMockMvc.perform(post("/api/th-selector-paths")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(thSelectorPaths)))
                .andExpect(status().isCreated());

        // Validate the ThSelectorPaths in the database
        List<ThSelectorPaths> thSelectorPaths = thSelectorPathsRepository.findAll();
        assertThat(thSelectorPaths).hasSize(databaseSizeBeforeCreate + 1);
        ThSelectorPaths testThSelectorPaths = thSelectorPaths.get(thSelectorPaths.size() - 1);
    }

    @Test
    @Transactional
    public void getAllThSelectorPaths() throws Exception {
        // Initialize the database
        thSelectorPathsRepository.saveAndFlush(thSelectorPaths);

        // Get all the thSelectorPaths
        restThSelectorPathsMockMvc.perform(get("/api/th-selector-paths?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(thSelectorPaths.getId().intValue())));
    }

    @Test
    @Transactional
    public void getThSelectorPaths() throws Exception {
        // Initialize the database
        thSelectorPathsRepository.saveAndFlush(thSelectorPaths);

        // Get the thSelectorPaths
        restThSelectorPathsMockMvc.perform(get("/api/th-selector-paths/{id}", thSelectorPaths.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(thSelectorPaths.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingThSelectorPaths() throws Exception {
        // Get the thSelectorPaths
        restThSelectorPathsMockMvc.perform(get("/api/th-selector-paths/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThSelectorPaths() throws Exception {
        // Initialize the database
        thSelectorPathsRepository.saveAndFlush(thSelectorPaths);
        int databaseSizeBeforeUpdate = thSelectorPathsRepository.findAll().size();

        // Update the thSelectorPaths
        ThSelectorPaths updatedThSelectorPaths = new ThSelectorPaths();
        updatedThSelectorPaths.setId(thSelectorPaths.getId());

        restThSelectorPathsMockMvc.perform(put("/api/th-selector-paths")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedThSelectorPaths)))
                .andExpect(status().isOk());

        // Validate the ThSelectorPaths in the database
        List<ThSelectorPaths> thSelectorPaths = thSelectorPathsRepository.findAll();
        assertThat(thSelectorPaths).hasSize(databaseSizeBeforeUpdate);
        ThSelectorPaths testThSelectorPaths = thSelectorPaths.get(thSelectorPaths.size() - 1);
    }

    @Test
    @Transactional
    public void deleteThSelectorPaths() throws Exception {
        // Initialize the database
        thSelectorPathsRepository.saveAndFlush(thSelectorPaths);
        int databaseSizeBeforeDelete = thSelectorPathsRepository.findAll().size();

        // Get the thSelectorPaths
        restThSelectorPathsMockMvc.perform(delete("/api/th-selector-paths/{id}", thSelectorPaths.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ThSelectorPaths> thSelectorPaths = thSelectorPathsRepository.findAll();
        assertThat(thSelectorPaths).hasSize(databaseSizeBeforeDelete - 1);
    }
}
