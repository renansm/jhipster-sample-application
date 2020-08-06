package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Paper;
import com.mycompany.myapp.repository.PaperRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PaperResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaperResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_MARKET_VALUE = 1L;
    private static final Long UPDATED_MARKET_VALUE = 2L;

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaperMockMvc;

    private Paper paper;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paper createEntity(EntityManager em) {
        Paper paper = new Paper()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .marketValue(DEFAULT_MARKET_VALUE);
        return paper;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paper createUpdatedEntity(EntityManager em) {
        Paper paper = new Paper()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .marketValue(UPDATED_MARKET_VALUE);
        return paper;
    }

    @BeforeEach
    public void initTest() {
        paper = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaper() throws Exception {
        int databaseSizeBeforeCreate = paperRepository.findAll().size();
        // Create the Paper
        restPaperMockMvc.perform(post("/api/papers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paper)))
            .andExpect(status().isCreated());

        // Validate the Paper in the database
        List<Paper> paperList = paperRepository.findAll();
        assertThat(paperList).hasSize(databaseSizeBeforeCreate + 1);
        Paper testPaper = paperList.get(paperList.size() - 1);
        assertThat(testPaper.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPaper.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaper.getMarketValue()).isEqualTo(DEFAULT_MARKET_VALUE);
    }

    @Test
    @Transactional
    public void createPaperWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paperRepository.findAll().size();

        // Create the Paper with an existing ID
        paper.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaperMockMvc.perform(post("/api/papers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paper)))
            .andExpect(status().isBadRequest());

        // Validate the Paper in the database
        List<Paper> paperList = paperRepository.findAll();
        assertThat(paperList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPapers() throws Exception {
        // Initialize the database
        paperRepository.saveAndFlush(paper);

        // Get all the paperList
        restPaperMockMvc.perform(get("/api/papers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paper.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].marketValue").value(hasItem(DEFAULT_MARKET_VALUE.intValue())));
    }
    
    @Test
    @Transactional
    public void getPaper() throws Exception {
        // Initialize the database
        paperRepository.saveAndFlush(paper);

        // Get the paper
        restPaperMockMvc.perform(get("/api/papers/{id}", paper.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paper.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.marketValue").value(DEFAULT_MARKET_VALUE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPaper() throws Exception {
        // Get the paper
        restPaperMockMvc.perform(get("/api/papers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaper() throws Exception {
        // Initialize the database
        paperRepository.saveAndFlush(paper);

        int databaseSizeBeforeUpdate = paperRepository.findAll().size();

        // Update the paper
        Paper updatedPaper = paperRepository.findById(paper.getId()).get();
        // Disconnect from session so that the updates on updatedPaper are not directly saved in db
        em.detach(updatedPaper);
        updatedPaper
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .marketValue(UPDATED_MARKET_VALUE);

        restPaperMockMvc.perform(put("/api/papers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaper)))
            .andExpect(status().isOk());

        // Validate the Paper in the database
        List<Paper> paperList = paperRepository.findAll();
        assertThat(paperList).hasSize(databaseSizeBeforeUpdate);
        Paper testPaper = paperList.get(paperList.size() - 1);
        assertThat(testPaper.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPaper.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaper.getMarketValue()).isEqualTo(UPDATED_MARKET_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingPaper() throws Exception {
        int databaseSizeBeforeUpdate = paperRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaperMockMvc.perform(put("/api/papers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paper)))
            .andExpect(status().isBadRequest());

        // Validate the Paper in the database
        List<Paper> paperList = paperRepository.findAll();
        assertThat(paperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaper() throws Exception {
        // Initialize the database
        paperRepository.saveAndFlush(paper);

        int databaseSizeBeforeDelete = paperRepository.findAll().size();

        // Delete the paper
        restPaperMockMvc.perform(delete("/api/papers/{id}", paper.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Paper> paperList = paperRepository.findAll();
        assertThat(paperList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
