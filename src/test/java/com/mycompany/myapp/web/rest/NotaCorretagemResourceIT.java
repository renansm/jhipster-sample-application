package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.NotaCorretagem;
import com.mycompany.myapp.repository.NotaCorretagemRepository;

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
 * Integration tests for the {@link NotaCorretagemResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NotaCorretagemResourceIT {

    private static final Long DEFAULT_NUMERO = 1L;
    private static final Long UPDATED_NUMERO = 2L;

    private static final Double DEFAULT_EMOLUMENTOS = 1D;
    private static final Double UPDATED_EMOLUMENTOS = 2D;

    private static final Double DEFAULT_LIQUIDACAO = 1D;
    private static final Double UPDATED_LIQUIDACAO = 2D;

    private static final Double DEFAULT_OUTRAS_TAXAS = 1D;
    private static final Double UPDATED_OUTRAS_TAXAS = 2D;

    private static final Double DEFAULT_VALOR = 1D;
    private static final Double UPDATED_VALOR = 2D;

    @Autowired
    private NotaCorretagemRepository notaCorretagemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotaCorretagemMockMvc;

    private NotaCorretagem notaCorretagem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotaCorretagem createEntity(EntityManager em) {
        NotaCorretagem notaCorretagem = new NotaCorretagem()
            .numero(DEFAULT_NUMERO)
            .emolumentos(DEFAULT_EMOLUMENTOS)
            .liquidacao(DEFAULT_LIQUIDACAO)
            .outrasTaxas(DEFAULT_OUTRAS_TAXAS)
            .valor(DEFAULT_VALOR);
        return notaCorretagem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotaCorretagem createUpdatedEntity(EntityManager em) {
        NotaCorretagem notaCorretagem = new NotaCorretagem()
            .numero(UPDATED_NUMERO)
            .emolumentos(UPDATED_EMOLUMENTOS)
            .liquidacao(UPDATED_LIQUIDACAO)
            .outrasTaxas(UPDATED_OUTRAS_TAXAS)
            .valor(UPDATED_VALOR);
        return notaCorretagem;
    }

    @BeforeEach
    public void initTest() {
        notaCorretagem = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotaCorretagem() throws Exception {
        int databaseSizeBeforeCreate = notaCorretagemRepository.findAll().size();
        // Create the NotaCorretagem
        restNotaCorretagemMockMvc.perform(post("/api/nota-corretagems").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notaCorretagem)))
            .andExpect(status().isCreated());

        // Validate the NotaCorretagem in the database
        List<NotaCorretagem> notaCorretagemList = notaCorretagemRepository.findAll();
        assertThat(notaCorretagemList).hasSize(databaseSizeBeforeCreate + 1);
        NotaCorretagem testNotaCorretagem = notaCorretagemList.get(notaCorretagemList.size() - 1);
        assertThat(testNotaCorretagem.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testNotaCorretagem.getEmolumentos()).isEqualTo(DEFAULT_EMOLUMENTOS);
        assertThat(testNotaCorretagem.getLiquidacao()).isEqualTo(DEFAULT_LIQUIDACAO);
        assertThat(testNotaCorretagem.getOutrasTaxas()).isEqualTo(DEFAULT_OUTRAS_TAXAS);
        assertThat(testNotaCorretagem.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createNotaCorretagemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notaCorretagemRepository.findAll().size();

        // Create the NotaCorretagem with an existing ID
        notaCorretagem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotaCorretagemMockMvc.perform(post("/api/nota-corretagems").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notaCorretagem)))
            .andExpect(status().isBadRequest());

        // Validate the NotaCorretagem in the database
        List<NotaCorretagem> notaCorretagemList = notaCorretagemRepository.findAll();
        assertThat(notaCorretagemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNotaCorretagems() throws Exception {
        // Initialize the database
        notaCorretagemRepository.saveAndFlush(notaCorretagem);

        // Get all the notaCorretagemList
        restNotaCorretagemMockMvc.perform(get("/api/nota-corretagems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notaCorretagem.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.intValue())))
            .andExpect(jsonPath("$.[*].emolumentos").value(hasItem(DEFAULT_EMOLUMENTOS.doubleValue())))
            .andExpect(jsonPath("$.[*].liquidacao").value(hasItem(DEFAULT_LIQUIDACAO.doubleValue())))
            .andExpect(jsonPath("$.[*].outrasTaxas").value(hasItem(DEFAULT_OUTRAS_TAXAS.doubleValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getNotaCorretagem() throws Exception {
        // Initialize the database
        notaCorretagemRepository.saveAndFlush(notaCorretagem);

        // Get the notaCorretagem
        restNotaCorretagemMockMvc.perform(get("/api/nota-corretagems/{id}", notaCorretagem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notaCorretagem.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.intValue()))
            .andExpect(jsonPath("$.emolumentos").value(DEFAULT_EMOLUMENTOS.doubleValue()))
            .andExpect(jsonPath("$.liquidacao").value(DEFAULT_LIQUIDACAO.doubleValue()))
            .andExpect(jsonPath("$.outrasTaxas").value(DEFAULT_OUTRAS_TAXAS.doubleValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingNotaCorretagem() throws Exception {
        // Get the notaCorretagem
        restNotaCorretagemMockMvc.perform(get("/api/nota-corretagems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotaCorretagem() throws Exception {
        // Initialize the database
        notaCorretagemRepository.saveAndFlush(notaCorretagem);

        int databaseSizeBeforeUpdate = notaCorretagemRepository.findAll().size();

        // Update the notaCorretagem
        NotaCorretagem updatedNotaCorretagem = notaCorretagemRepository.findById(notaCorretagem.getId()).get();
        // Disconnect from session so that the updates on updatedNotaCorretagem are not directly saved in db
        em.detach(updatedNotaCorretagem);
        updatedNotaCorretagem
            .numero(UPDATED_NUMERO)
            .emolumentos(UPDATED_EMOLUMENTOS)
            .liquidacao(UPDATED_LIQUIDACAO)
            .outrasTaxas(UPDATED_OUTRAS_TAXAS)
            .valor(UPDATED_VALOR);

        restNotaCorretagemMockMvc.perform(put("/api/nota-corretagems").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedNotaCorretagem)))
            .andExpect(status().isOk());

        // Validate the NotaCorretagem in the database
        List<NotaCorretagem> notaCorretagemList = notaCorretagemRepository.findAll();
        assertThat(notaCorretagemList).hasSize(databaseSizeBeforeUpdate);
        NotaCorretagem testNotaCorretagem = notaCorretagemList.get(notaCorretagemList.size() - 1);
        assertThat(testNotaCorretagem.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testNotaCorretagem.getEmolumentos()).isEqualTo(UPDATED_EMOLUMENTOS);
        assertThat(testNotaCorretagem.getLiquidacao()).isEqualTo(UPDATED_LIQUIDACAO);
        assertThat(testNotaCorretagem.getOutrasTaxas()).isEqualTo(UPDATED_OUTRAS_TAXAS);
        assertThat(testNotaCorretagem.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingNotaCorretagem() throws Exception {
        int databaseSizeBeforeUpdate = notaCorretagemRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotaCorretagemMockMvc.perform(put("/api/nota-corretagems").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notaCorretagem)))
            .andExpect(status().isBadRequest());

        // Validate the NotaCorretagem in the database
        List<NotaCorretagem> notaCorretagemList = notaCorretagemRepository.findAll();
        assertThat(notaCorretagemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNotaCorretagem() throws Exception {
        // Initialize the database
        notaCorretagemRepository.saveAndFlush(notaCorretagem);

        int databaseSizeBeforeDelete = notaCorretagemRepository.findAll().size();

        // Delete the notaCorretagem
        restNotaCorretagemMockMvc.perform(delete("/api/nota-corretagems/{id}", notaCorretagem.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NotaCorretagem> notaCorretagemList = notaCorretagemRepository.findAll();
        assertThat(notaCorretagemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
