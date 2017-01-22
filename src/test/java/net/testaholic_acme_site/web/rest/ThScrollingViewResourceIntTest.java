package net.testaholic_acme_site.web.rest;

import net.testaholic_acme_site.AcmeSiteApp;
import net.testaholic_acme_site.domain.ThScrollingView;
import net.testaholic_acme_site.repository.ThScrollingViewRepository;

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
 * Test class for the ThScrollingViewResource REST controller.
 *
 * @see ThScrollingViewResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AcmeSiteApp.class)
@WebAppConfiguration
@IntegrationTest
public class ThScrollingViewResourceIntTest {


    @Inject
    private ThScrollingViewRepository thScrollingViewRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restThScrollingViewMockMvc;

    private ThScrollingView thScrollingView;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThScrollingViewResource thScrollingViewResource = new ThScrollingViewResource();
        ReflectionTestUtils.setField(thScrollingViewResource, "thScrollingViewRepository", thScrollingViewRepository);
        this.restThScrollingViewMockMvc = MockMvcBuilders.standaloneSetup(thScrollingViewResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        thScrollingView = new ThScrollingView();
    }

    @Test
    @Transactional
    public void createThScrollingView() throws Exception {
        int databaseSizeBeforeCreate = thScrollingViewRepository.findAll().size();

        // Create the ThScrollingView

        restThScrollingViewMockMvc.perform(post("/api/th-scrolling-views")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(thScrollingView)))
                .andExpect(status().isCreated());

        // Validate the ThScrollingView in the database
        List<ThScrollingView> thScrollingViews = thScrollingViewRepository.findAll();
        assertThat(thScrollingViews).hasSize(databaseSizeBeforeCreate + 1);
        ThScrollingView testThScrollingView = thScrollingViews.get(thScrollingViews.size() - 1);
    }

    @Test
    @Transactional
    public void getAllThScrollingViews() throws Exception {
        // Initialize the database
        thScrollingViewRepository.saveAndFlush(thScrollingView);

        // Get all the thScrollingViews
        restThScrollingViewMockMvc.perform(get("/api/th-scrolling-views?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(thScrollingView.getId().intValue())));
    }

    @Test
    @Transactional
    public void getThScrollingView() throws Exception {
        // Initialize the database
        thScrollingViewRepository.saveAndFlush(thScrollingView);

        // Get the thScrollingView
        restThScrollingViewMockMvc.perform(get("/api/th-scrolling-views/{id}", thScrollingView.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(thScrollingView.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingThScrollingView() throws Exception {
        // Get the thScrollingView
        restThScrollingViewMockMvc.perform(get("/api/th-scrolling-views/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThScrollingView() throws Exception {
        // Initialize the database
        thScrollingViewRepository.saveAndFlush(thScrollingView);
        int databaseSizeBeforeUpdate = thScrollingViewRepository.findAll().size();

        // Update the thScrollingView
        ThScrollingView updatedThScrollingView = new ThScrollingView();
        updatedThScrollingView.setId(thScrollingView.getId());

        restThScrollingViewMockMvc.perform(put("/api/th-scrolling-views")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedThScrollingView)))
                .andExpect(status().isOk());

        // Validate the ThScrollingView in the database
        List<ThScrollingView> thScrollingViews = thScrollingViewRepository.findAll();
        assertThat(thScrollingViews).hasSize(databaseSizeBeforeUpdate);
        ThScrollingView testThScrollingView = thScrollingViews.get(thScrollingViews.size() - 1);
    }

    @Test
    @Transactional
    public void deleteThScrollingView() throws Exception {
        // Initialize the database
        thScrollingViewRepository.saveAndFlush(thScrollingView);
        int databaseSizeBeforeDelete = thScrollingViewRepository.findAll().size();

        // Get the thScrollingView
        restThScrollingViewMockMvc.perform(delete("/api/th-scrolling-views/{id}", thScrollingView.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ThScrollingView> thScrollingViews = thScrollingViewRepository.findAll();
        assertThat(thScrollingViews).hasSize(databaseSizeBeforeDelete - 1);
    }
}
