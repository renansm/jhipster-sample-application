package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Transacao;
import com.mycompany.myapp.repository.TransacaoRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.TipoTransacao;
/**
 * Integration tests for the {@link TransacaoResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TransacaoResourceIT {

    private static final Long DEFAULT_QUANTIDADE = 1L;
    private static final Long UPDATED_QUANTIDADE = 2L;

    private static final Double DEFAULT_VALOR = 1D;
    private static final Double UPDATED_VALOR = 2D;

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final TipoTransacao DEFAULT_TIPO = TipoTransacao.COMPRA;
    private static final TipoTransacao UPDATED_TIPO = TipoTransacao.VENDA;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransacaoMockMvc;

    private Transacao transacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transacao createEntity(EntityManager em) {
        Transacao transacao = new Transacao()
            .quantidade(DEFAULT_QUANTIDADE)
            .valor(DEFAULT_VALOR)
            .data(DEFAULT_DATA)
            .tipo(DEFAULT_TIPO);
        return transacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transacao createUpdatedEntity(EntityManager em) {
        Transacao transacao = new Transacao()
            .quantidade(UPDATED_QUANTIDADE)
            .valor(UPDATED_VALOR)
            .data(UPDATED_DATA)
            .tipo(UPDATED_TIPO);
        return transacao;
    }

    @BeforeEach
    public void initTest() {
        transacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransacao() throws Exception {
        int databaseSizeBeforeCreate = transacaoRepository.findAll().size();
        // Create the Transacao
        restTransacaoMockMvc.perform(post("/api/transacaos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transacao)))
            .andExpect(status().isCreated());

        // Validate the Transacao in the database
        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Transacao testTransacao = transacaoList.get(transacaoList.size() - 1);
        assertThat(testTransacao.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testTransacao.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testTransacao.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testTransacao.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createTransacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transacaoRepository.findAll().size();

        // Create the Transacao with an existing ID
        transacao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransacaoMockMvc.perform(post("/api/transacaos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transacao)))
            .andExpect(status().isBadRequest());

        // Validate the Transacao in the database
        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTransacaos() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList
        restTransacaoMockMvc.perform(get("/api/transacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }
    
    @Test
    @Transactional
    public void getTransacao() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get the transacao
        restTransacaoMockMvc.perform(get("/api/transacaos/{id}", transacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transacao.getId().intValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE.intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTransacao() throws Exception {
        // Get the transacao
        restTransacaoMockMvc.perform(get("/api/transacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransacao() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        int databaseSizeBeforeUpdate = transacaoRepository.findAll().size();

        // Update the transacao
        Transacao updatedTransacao = transacaoRepository.findById(transacao.getId()).get();
        // Disconnect from session so that the updates on updatedTransacao are not directly saved in db
        em.detach(updatedTransacao);
        updatedTransacao
            .quantidade(UPDATED_QUANTIDADE)
            .valor(UPDATED_VALOR)
            .data(UPDATED_DATA)
            .tipo(UPDATED_TIPO);

        restTransacaoMockMvc.perform(put("/api/transacaos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransacao)))
            .andExpect(status().isOk());

        // Validate the Transacao in the database
        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeUpdate);
        Transacao testTransacao = transacaoList.get(transacaoList.size() - 1);
        assertThat(testTransacao.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testTransacao.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testTransacao.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testTransacao.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingTransacao() throws Exception {
        int databaseSizeBeforeUpdate = transacaoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransacaoMockMvc.perform(put("/api/transacaos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transacao)))
            .andExpect(status().isBadRequest());

        // Validate the Transacao in the database
        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransacao() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        int databaseSizeBeforeDelete = transacaoRepository.findAll().size();

        // Delete the transacao
        restTransacaoMockMvc.perform(delete("/api/transacaos/{id}", transacao.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
