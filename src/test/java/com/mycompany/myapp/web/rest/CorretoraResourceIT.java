package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Corretora;
import com.mycompany.myapp.repository.CorretoraRepository;

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
 * Integration tests for the {@link CorretoraResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CorretoraResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private CorretoraRepository corretoraRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCorretoraMockMvc;

    private Corretora corretora;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Corretora createEntity(EntityManager em) {
        Corretora corretora = new Corretora()
            .nome(DEFAULT_NOME);
        return corretora;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Corretora createUpdatedEntity(EntityManager em) {
        Corretora corretora = new Corretora()
            .nome(UPDATED_NOME);
        return corretora;
    }

    @BeforeEach
    public void initTest() {
        corretora = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorretora() throws Exception {
        int databaseSizeBeforeCreate = corretoraRepository.findAll().size();
        // Create the Corretora
        restCorretoraMockMvc.perform(post("/api/corretoras").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corretora)))
            .andExpect(status().isCreated());

        // Validate the Corretora in the database
        List<Corretora> corretoraList = corretoraRepository.findAll();
        assertThat(corretoraList).hasSize(databaseSizeBeforeCreate + 1);
        Corretora testCorretora = corretoraList.get(corretoraList.size() - 1);
        assertThat(testCorretora.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createCorretoraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corretoraRepository.findAll().size();

        // Create the Corretora with an existing ID
        corretora.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorretoraMockMvc.perform(post("/api/corretoras").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corretora)))
            .andExpect(status().isBadRequest());

        // Validate the Corretora in the database
        List<Corretora> corretoraList = corretoraRepository.findAll();
        assertThat(corretoraList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCorretoras() throws Exception {
        // Initialize the database
        corretoraRepository.saveAndFlush(corretora);

        // Get all the corretoraList
        restCorretoraMockMvc.perform(get("/api/corretoras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corretora.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getCorretora() throws Exception {
        // Initialize the database
        corretoraRepository.saveAndFlush(corretora);

        // Get the corretora
        restCorretoraMockMvc.perform(get("/api/corretoras/{id}", corretora.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(corretora.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }
    @Test
    @Transactional
    public void getNonExistingCorretora() throws Exception {
        // Get the corretora
        restCorretoraMockMvc.perform(get("/api/corretoras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorretora() throws Exception {
        // Initialize the database
        corretoraRepository.saveAndFlush(corretora);

        int databaseSizeBeforeUpdate = corretoraRepository.findAll().size();

        // Update the corretora
        Corretora updatedCorretora = corretoraRepository.findById(corretora.getId()).get();
        // Disconnect from session so that the updates on updatedCorretora are not directly saved in db
        em.detach(updatedCorretora);
        updatedCorretora
            .nome(UPDATED_NOME);

        restCorretoraMockMvc.perform(put("/api/corretoras").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCorretora)))
            .andExpect(status().isOk());

        // Validate the Corretora in the database
        List<Corretora> corretoraList = corretoraRepository.findAll();
        assertThat(corretoraList).hasSize(databaseSizeBeforeUpdate);
        Corretora testCorretora = corretoraList.get(corretoraList.size() - 1);
        assertThat(testCorretora.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingCorretora() throws Exception {
        int databaseSizeBeforeUpdate = corretoraRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCorretoraMockMvc.perform(put("/api/corretoras").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corretora)))
            .andExpect(status().isBadRequest());

        // Validate the Corretora in the database
        List<Corretora> corretoraList = corretoraRepository.findAll();
        assertThat(corretoraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCorretora() throws Exception {
        // Initialize the database
        corretoraRepository.saveAndFlush(corretora);

        int databaseSizeBeforeDelete = corretoraRepository.findAll().size();

        // Delete the corretora
        restCorretoraMockMvc.perform(delete("/api/corretoras/{id}", corretora.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Corretora> corretoraList = corretoraRepository.findAll();
        assertThat(corretoraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
