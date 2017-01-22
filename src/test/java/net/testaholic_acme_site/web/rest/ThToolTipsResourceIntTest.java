package net.testaholic_acme_site.web.rest;

import net.testaholic_acme_site.AcmeSiteApp;
import net.testaholic_acme_site.domain.ThToolTips;
import net.testaholic_acme_site.repository.ThToolTipsRepository;

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
 * Test class for the ThToolTipsResource REST controller.
 *
 * @see ThToolTipsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AcmeSiteApp.class)
@WebAppConfiguration
@IntegrationTest
public class ThToolTipsResourceIntTest {


    @Inject
    private ThToolTipsRepository thToolTipsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restThToolTipsMockMvc;

    private ThToolTips thToolTips;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThToolTipsResource thToolTipsResource = new ThToolTipsResource();
        ReflectionTestUtils.setField(thToolTipsResource, "thToolTipsRepository", thToolTipsRepository);
        this.restThToolTipsMockMvc = MockMvcBuilders.standaloneSetup(thToolTipsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        thToolTips = new ThToolTips();
    }

    @Test
    @Transactional
    public void createThToolTips() throws Exception {
        int databaseSizeBeforeCreate = thToolTipsRepository.findAll().size();

        // Create the ThToolTips

        restThToolTipsMockMvc.perform(post("/api/th-tool-tips")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(thToolTips)))
                .andExpect(status().isCreated());

        // Validate the ThToolTips in the database
        List<ThToolTips> thToolTips = thToolTipsRepository.findAll();
        assertThat(thToolTips).hasSize(databaseSizeBeforeCreate + 1);
        ThToolTips testThToolTips = thToolTips.get(thToolTips.size() - 1);
    }

    @Test
    @Transactional
    public void getAllThToolTips() throws Exception {
        // Initialize the database
        thToolTipsRepository.saveAndFlush(thToolTips);

        // Get all the thToolTips
        restThToolTipsMockMvc.perform(get("/api/th-tool-tips?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(thToolTips.getId().intValue())));
    }

    @Test
    @Transactional
    public void getThToolTips() throws Exception {
        // Initialize the database
        thToolTipsRepository.saveAndFlush(thToolTips);

        // Get the thToolTips
        restThToolTipsMockMvc.perform(get("/api/th-tool-tips/{id}", thToolTips.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(thToolTips.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingThToolTips() throws Exception {
        // Get the thToolTips
        restThToolTipsMockMvc.perform(get("/api/th-tool-tips/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThToolTips() throws Exception {
        // Initialize the database
        thToolTipsRepository.saveAndFlush(thToolTips);
        int databaseSizeBeforeUpdate = thToolTipsRepository.findAll().size();

        // Update the thToolTips
        ThToolTips updatedThToolTips = new ThToolTips();
        updatedThToolTips.setId(thToolTips.getId());

        restThToolTipsMockMvc.perform(put("/api/th-tool-tips")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedThToolTips)))
                .andExpect(status().isOk());

        // Validate the ThToolTips in the database
        List<ThToolTips> thToolTips = thToolTipsRepository.findAll();
        assertThat(thToolTips).hasSize(databaseSizeBeforeUpdate);
        ThToolTips testThToolTips = thToolTips.get(thToolTips.size() - 1);
    }

    @Test
    @Transactional
    public void deleteThToolTips() throws Exception {
        // Initialize the database
        thToolTipsRepository.saveAndFlush(thToolTips);
        int databaseSizeBeforeDelete = thToolTipsRepository.findAll().size();

        // Get the thToolTips
        restThToolTipsMockMvc.perform(delete("/api/th-tool-tips/{id}", thToolTips.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ThToolTips> thToolTips = thToolTipsRepository.findAll();
        assertThat(thToolTips).hasSize(databaseSizeBeforeDelete - 1);
    }
}
