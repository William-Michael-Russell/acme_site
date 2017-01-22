package net.testaholic_acme_site.web.rest;

import net.testaholic_acme_site.AcmeSiteApp;
import net.testaholic_acme_site.domain.ThGUI;
import net.testaholic_acme_site.repository.ThGUIRepository;

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
 * Test class for the ThGUIResource REST controller.
 *
 * @see ThGUIResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AcmeSiteApp.class)
@WebAppConfiguration
@IntegrationTest
public class ThGUIResourceIntTest {


    @Inject
    private ThGUIRepository thGUIRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restThGUIMockMvc;

    private ThGUI thGUI;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThGUIResource thGUIResource = new ThGUIResource();
        ReflectionTestUtils.setField(thGUIResource, "thGUIRepository", thGUIRepository);
        this.restThGUIMockMvc = MockMvcBuilders.standaloneSetup(thGUIResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        thGUI = new ThGUI();
    }

    @Test
    @Transactional
    public void createThGUI() throws Exception {
        int databaseSizeBeforeCreate = thGUIRepository.findAll().size();

        // Create the ThGUI

        restThGUIMockMvc.perform(post("/api/th-guis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(thGUI)))
                .andExpect(status().isCreated());

        // Validate the ThGUI in the database
        List<ThGUI> thGUIS = thGUIRepository.findAll();
        assertThat(thGUIS).hasSize(databaseSizeBeforeCreate + 1);
        ThGUI testThGUI = thGUIS.get(thGUIS.size() - 1);
    }

    @Test
    @Transactional
    public void getAllThGUIS() throws Exception {
        // Initialize the database
        thGUIRepository.saveAndFlush(thGUI);

        // Get all the thGUIS
        restThGUIMockMvc.perform(get("/api/th-guis?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(thGUI.getId().intValue())));
    }

    @Test
    @Transactional
    public void getThGUI() throws Exception {
        // Initialize the database
        thGUIRepository.saveAndFlush(thGUI);

        // Get the thGUI
        restThGUIMockMvc.perform(get("/api/th-guis/{id}", thGUI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(thGUI.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingThGUI() throws Exception {
        // Get the thGUI
        restThGUIMockMvc.perform(get("/api/th-guis/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThGUI() throws Exception {
        // Initialize the database
        thGUIRepository.saveAndFlush(thGUI);
        int databaseSizeBeforeUpdate = thGUIRepository.findAll().size();

        // Update the thGUI
        ThGUI updatedThGUI = new ThGUI();
        updatedThGUI.setId(thGUI.getId());

        restThGUIMockMvc.perform(put("/api/th-guis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedThGUI)))
                .andExpect(status().isOk());

        // Validate the ThGUI in the database
        List<ThGUI> thGUIS = thGUIRepository.findAll();
        assertThat(thGUIS).hasSize(databaseSizeBeforeUpdate);
        ThGUI testThGUI = thGUIS.get(thGUIS.size() - 1);
    }

    @Test
    @Transactional
    public void deleteThGUI() throws Exception {
        // Initialize the database
        thGUIRepository.saveAndFlush(thGUI);
        int databaseSizeBeforeDelete = thGUIRepository.findAll().size();

        // Get the thGUI
        restThGUIMockMvc.perform(delete("/api/th-guis/{id}", thGUI.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ThGUI> thGUIS = thGUIRepository.findAll();
        assertThat(thGUIS).hasSize(databaseSizeBeforeDelete - 1);
    }
}
