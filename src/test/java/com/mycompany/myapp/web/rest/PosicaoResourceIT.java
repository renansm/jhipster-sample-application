package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Posicao;
import com.mycompany.myapp.repository.PosicaoRepository;

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
 * Integration tests for the {@link PosicaoResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PosicaoResourceIT {

    private static final Long DEFAULT_QUANTIDADE = 1L;
    private static final Long UPDATED_QUANTIDADE = 2L;

    @Autowired
    private PosicaoRepository posicaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPosicaoMockMvc;

    private Posicao posicao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Posicao createEntity(EntityManager em) {
        Posicao posicao = new Posicao()
            .quantidade(DEFAULT_QUANTIDADE);
        return posicao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Posicao createUpdatedEntity(EntityManager em) {
        Posicao posicao = new Posicao()
            .quantidade(UPDATED_QUANTIDADE);
        return posicao;
    }

    @BeforeEach
    public void initTest() {
        posicao = createEntity(em);
    }

    @Test
    @Transactional
    public void createPosicao() throws Exception {
        int databaseSizeBeforeCreate = posicaoRepository.findAll().size();
        // Create the Posicao
        restPosicaoMockMvc.perform(post("/api/posicaos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(posicao)))
            .andExpect(status().isCreated());

        // Validate the Posicao in the database
        List<Posicao> posicaoList = posicaoRepository.findAll();
        assertThat(posicaoList).hasSize(databaseSizeBeforeCreate + 1);
        Posicao testPosicao = posicaoList.get(posicaoList.size() - 1);
        assertThat(testPosicao.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
    }

    @Test
    @Transactional
    public void createPosicaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = posicaoRepository.findAll().size();

        // Create the Posicao with an existing ID
        posicao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPosicaoMockMvc.perform(post("/api/posicaos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(posicao)))
            .andExpect(status().isBadRequest());

        // Validate the Posicao in the database
        List<Posicao> posicaoList = posicaoRepository.findAll();
        assertThat(posicaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPosicaos() throws Exception {
        // Initialize the database
        posicaoRepository.saveAndFlush(posicao);

        // Get all the posicaoList
        restPosicaoMockMvc.perform(get("/api/posicaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(posicao.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.intValue())));
    }
    
    @Test
    @Transactional
    public void getPosicao() throws Exception {
        // Initialize the database
        posicaoRepository.saveAndFlush(posicao);

        // Get the posicao
        restPosicaoMockMvc.perform(get("/api/posicaos/{id}", posicao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(posicao.getId().intValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPosicao() throws Exception {
        // Get the posicao
        restPosicaoMockMvc.perform(get("/api/posicaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePosicao() throws Exception {
        // Initialize the database
        posicaoRepository.saveAndFlush(posicao);

        int databaseSizeBeforeUpdate = posicaoRepository.findAll().size();

        // Update the posicao
        Posicao updatedPosicao = posicaoRepository.findById(posicao.getId()).get();
        // Disconnect from session so that the updates on updatedPosicao are not directly saved in db
        em.detach(updatedPosicao);
        updatedPosicao
            .quantidade(UPDATED_QUANTIDADE);

        restPosicaoMockMvc.perform(put("/api/posicaos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPosicao)))
            .andExpect(status().isOk());

        // Validate the Posicao in the database
        List<Posicao> posicaoList = posicaoRepository.findAll();
        assertThat(posicaoList).hasSize(databaseSizeBeforeUpdate);
        Posicao testPosicao = posicaoList.get(posicaoList.size() - 1);
        assertThat(testPosicao.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void updateNonExistingPosicao() throws Exception {
        int databaseSizeBeforeUpdate = posicaoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPosicaoMockMvc.perform(put("/api/posicaos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(posicao)))
            .andExpect(status().isBadRequest());

        // Validate the Posicao in the database
        List<Posicao> posicaoList = posicaoRepository.findAll();
        assertThat(posicaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePosicao() throws Exception {
        // Initialize the database
        posicaoRepository.saveAndFlush(posicao);

        int databaseSizeBeforeDelete = posicaoRepository.findAll().size();

        // Delete the posicao
        restPosicaoMockMvc.perform(delete("/api/posicaos/{id}", posicao.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Posicao> posicaoList = posicaoRepository.findAll();
        assertThat(posicaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
