package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Papel;
import com.mycompany.myapp.repository.PapelRepository;

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
 * Integration tests for the {@link PapelResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PapelResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECO_MERCADO = 1D;
    private static final Double UPDATED_PRECO_MERCADO = 2D;

    @Autowired
    private PapelRepository papelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPapelMockMvc;

    private Papel papel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Papel createEntity(EntityManager em) {
        Papel papel = new Papel()
            .codigo(DEFAULT_CODIGO)
            .nome(DEFAULT_NOME)
            .precoMercado(DEFAULT_PRECO_MERCADO);
        return papel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Papel createUpdatedEntity(EntityManager em) {
        Papel papel = new Papel()
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .precoMercado(UPDATED_PRECO_MERCADO);
        return papel;
    }

    @BeforeEach
    public void initTest() {
        papel = createEntity(em);
    }

    @Test
    @Transactional
    public void createPapel() throws Exception {
        int databaseSizeBeforeCreate = papelRepository.findAll().size();
        // Create the Papel
        restPapelMockMvc.perform(post("/api/papels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(papel)))
            .andExpect(status().isCreated());

        // Validate the Papel in the database
        List<Papel> papelList = papelRepository.findAll();
        assertThat(papelList).hasSize(databaseSizeBeforeCreate + 1);
        Papel testPapel = papelList.get(papelList.size() - 1);
        assertThat(testPapel.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testPapel.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPapel.getPrecoMercado()).isEqualTo(DEFAULT_PRECO_MERCADO);
    }

    @Test
    @Transactional
    public void createPapelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = papelRepository.findAll().size();

        // Create the Papel with an existing ID
        papel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPapelMockMvc.perform(post("/api/papels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(papel)))
            .andExpect(status().isBadRequest());

        // Validate the Papel in the database
        List<Papel> papelList = papelRepository.findAll();
        assertThat(papelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPapels() throws Exception {
        // Initialize the database
        papelRepository.saveAndFlush(papel);

        // Get all the papelList
        restPapelMockMvc.perform(get("/api/papels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(papel.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].precoMercado").value(hasItem(DEFAULT_PRECO_MERCADO.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getPapel() throws Exception {
        // Initialize the database
        papelRepository.saveAndFlush(papel);

        // Get the papel
        restPapelMockMvc.perform(get("/api/papels/{id}", papel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(papel.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.precoMercado").value(DEFAULT_PRECO_MERCADO.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPapel() throws Exception {
        // Get the papel
        restPapelMockMvc.perform(get("/api/papels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePapel() throws Exception {
        // Initialize the database
        papelRepository.saveAndFlush(papel);

        int databaseSizeBeforeUpdate = papelRepository.findAll().size();

        // Update the papel
        Papel updatedPapel = papelRepository.findById(papel.getId()).get();
        // Disconnect from session so that the updates on updatedPapel are not directly saved in db
        em.detach(updatedPapel);
        updatedPapel
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .precoMercado(UPDATED_PRECO_MERCADO);

        restPapelMockMvc.perform(put("/api/papels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPapel)))
            .andExpect(status().isOk());

        // Validate the Papel in the database
        List<Papel> papelList = papelRepository.findAll();
        assertThat(papelList).hasSize(databaseSizeBeforeUpdate);
        Papel testPapel = papelList.get(papelList.size() - 1);
        assertThat(testPapel.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testPapel.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPapel.getPrecoMercado()).isEqualTo(UPDATED_PRECO_MERCADO);
    }

    @Test
    @Transactional
    public void updateNonExistingPapel() throws Exception {
        int databaseSizeBeforeUpdate = papelRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPapelMockMvc.perform(put("/api/papels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(papel)))
            .andExpect(status().isBadRequest());

        // Validate the Papel in the database
        List<Papel> papelList = papelRepository.findAll();
        assertThat(papelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePapel() throws Exception {
        // Initialize the database
        papelRepository.saveAndFlush(papel);

        int databaseSizeBeforeDelete = papelRepository.findAll().size();

        // Delete the papel
        restPapelMockMvc.perform(delete("/api/papels/{id}", papel.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Papel> papelList = papelRepository.findAll();
        assertThat(papelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
