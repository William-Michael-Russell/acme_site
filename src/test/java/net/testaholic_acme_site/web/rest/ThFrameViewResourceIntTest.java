package net.testaholic_acme_site.web.rest;

import net.testaholic_acme_site.AcmeSiteApp;
import net.testaholic_acme_site.domain.ThFrameView;
import net.testaholic_acme_site.repository.ThFrameViewRepository;

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
 * Test class for the ThFrameViewResource REST controller.
 *
 * @see ThFrameViewResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AcmeSiteApp.class)
@WebAppConfiguration
@IntegrationTest
public class ThFrameViewResourceIntTest {


    @Inject
    private ThFrameViewRepository thFrameViewRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restThFrameViewMockMvc;

    private ThFrameView thFrameView;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThFrameViewResource thFrameViewResource = new ThFrameViewResource();
        ReflectionTestUtils.setField(thFrameViewResource, "thFrameViewRepository", thFrameViewRepository);
        this.restThFrameViewMockMvc = MockMvcBuilders.standaloneSetup(thFrameViewResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        thFrameView = new ThFrameView();
    }

    @Test
    @Transactional
    public void createThFrameView() throws Exception {
        int databaseSizeBeforeCreate = thFrameViewRepository.findAll().size();

        // Create the ThFrameView

        restThFrameViewMockMvc.perform(post("/api/th-frame-views")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(thFrameView)))
                .andExpect(status().isCreated());

        // Validate the ThFrameView in the database
        List<ThFrameView> thFrameViews = thFrameViewRepository.findAll();
        assertThat(thFrameViews).hasSize(databaseSizeBeforeCreate + 1);
        ThFrameView testThFrameView = thFrameViews.get(thFrameViews.size() - 1);
    }

    @Test
    @Transactional
    public void getAllThFrameViews() throws Exception {
        // Initialize the database
        thFrameViewRepository.saveAndFlush(thFrameView);

        // Get all the thFrameViews
        restThFrameViewMockMvc.perform(get("/api/th-frame-views?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(thFrameView.getId().intValue())));
    }

    @Test
    @Transactional
    public void getThFrameView() throws Exception {
        // Initialize the database
        thFrameViewRepository.saveAndFlush(thFrameView);

        // Get the thFrameView
        restThFrameViewMockMvc.perform(get("/api/th-frame-views/{id}", thFrameView.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(thFrameView.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingThFrameView() throws Exception {
        // Get the thFrameView
        restThFrameViewMockMvc.perform(get("/api/th-frame-views/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThFrameView() throws Exception {
        // Initialize the database
        thFrameViewRepository.saveAndFlush(thFrameView);
        int databaseSizeBeforeUpdate = thFrameViewRepository.findAll().size();

        // Update the thFrameView
        ThFrameView updatedThFrameView = new ThFrameView();
        updatedThFrameView.setId(thFrameView.getId());

        restThFrameViewMockMvc.perform(put("/api/th-frame-views")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedThFrameView)))
                .andExpect(status().isOk());

        // Validate the ThFrameView in the database
        List<ThFrameView> thFrameViews = thFrameViewRepository.findAll();
        assertThat(thFrameViews).hasSize(databaseSizeBeforeUpdate);
        ThFrameView testThFrameView = thFrameViews.get(thFrameViews.size() - 1);
    }

    @Test
    @Transactional
    public void deleteThFrameView() throws Exception {
        // Initialize the database
        thFrameViewRepository.saveAndFlush(thFrameView);
        int databaseSizeBeforeDelete = thFrameViewRepository.findAll().size();

        // Get the thFrameView
        restThFrameViewMockMvc.perform(delete("/api/th-frame-views/{id}", thFrameView.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ThFrameView> thFrameViews = thFrameViewRepository.findAll();
        assertThat(thFrameViews).hasSize(databaseSizeBeforeDelete - 1);
    }
}
