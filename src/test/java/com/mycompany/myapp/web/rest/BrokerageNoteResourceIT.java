package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.BrokerageNote;
import com.mycompany.myapp.repository.BrokerageNoteRepository;

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
 * Integration tests for the {@link BrokerageNoteResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BrokerageNoteResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final Long DEFAULT_EMOLUMENTS = 1L;
    private static final Long UPDATED_EMOLUMENTS = 2L;

    private static final Long DEFAULT_LIQUIDATION = 1L;
    private static final Long UPDATED_LIQUIDATION = 2L;

    private static final Long DEFAULT_OTHER_TAXES = 1L;
    private static final Long UPDATED_OTHER_TAXES = 2L;

    private static final Long DEFAULT_VALUE = 1L;
    private static final Long UPDATED_VALUE = 2L;

    @Autowired
    private BrokerageNoteRepository brokerageNoteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBrokerageNoteMockMvc;

    private BrokerageNote brokerageNote;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BrokerageNote createEntity(EntityManager em) {
        BrokerageNote brokerageNote = new BrokerageNote()
            .number(DEFAULT_NUMBER)
            .emoluments(DEFAULT_EMOLUMENTS)
            .liquidation(DEFAULT_LIQUIDATION)
            .otherTaxes(DEFAULT_OTHER_TAXES)
            .value(DEFAULT_VALUE);
        return brokerageNote;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BrokerageNote createUpdatedEntity(EntityManager em) {
        BrokerageNote brokerageNote = new BrokerageNote()
            .number(UPDATED_NUMBER)
            .emoluments(UPDATED_EMOLUMENTS)
            .liquidation(UPDATED_LIQUIDATION)
            .otherTaxes(UPDATED_OTHER_TAXES)
            .value(UPDATED_VALUE);
        return brokerageNote;
    }

    @BeforeEach
    public void initTest() {
        brokerageNote = createEntity(em);
    }

    @Test
    @Transactional
    public void createBrokerageNote() throws Exception {
        int databaseSizeBeforeCreate = brokerageNoteRepository.findAll().size();
        // Create the BrokerageNote
        restBrokerageNoteMockMvc.perform(post("/api/brokerage-notes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(brokerageNote)))
            .andExpect(status().isCreated());

        // Validate the BrokerageNote in the database
        List<BrokerageNote> brokerageNoteList = brokerageNoteRepository.findAll();
        assertThat(brokerageNoteList).hasSize(databaseSizeBeforeCreate + 1);
        BrokerageNote testBrokerageNote = brokerageNoteList.get(brokerageNoteList.size() - 1);
        assertThat(testBrokerageNote.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testBrokerageNote.getEmoluments()).isEqualTo(DEFAULT_EMOLUMENTS);
        assertThat(testBrokerageNote.getLiquidation()).isEqualTo(DEFAULT_LIQUIDATION);
        assertThat(testBrokerageNote.getOtherTaxes()).isEqualTo(DEFAULT_OTHER_TAXES);
        assertThat(testBrokerageNote.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createBrokerageNoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = brokerageNoteRepository.findAll().size();

        // Create the BrokerageNote with an existing ID
        brokerageNote.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBrokerageNoteMockMvc.perform(post("/api/brokerage-notes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(brokerageNote)))
            .andExpect(status().isBadRequest());

        // Validate the BrokerageNote in the database
        List<BrokerageNote> brokerageNoteList = brokerageNoteRepository.findAll();
        assertThat(brokerageNoteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBrokerageNotes() throws Exception {
        // Initialize the database
        brokerageNoteRepository.saveAndFlush(brokerageNote);

        // Get all the brokerageNoteList
        restBrokerageNoteMockMvc.perform(get("/api/brokerage-notes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brokerageNote.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].emoluments").value(hasItem(DEFAULT_EMOLUMENTS.intValue())))
            .andExpect(jsonPath("$.[*].liquidation").value(hasItem(DEFAULT_LIQUIDATION.intValue())))
            .andExpect(jsonPath("$.[*].otherTaxes").value(hasItem(DEFAULT_OTHER_TAXES.intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())));
    }
    
    @Test
    @Transactional
    public void getBrokerageNote() throws Exception {
        // Initialize the database
        brokerageNoteRepository.saveAndFlush(brokerageNote);

        // Get the brokerageNote
        restBrokerageNoteMockMvc.perform(get("/api/brokerage-notes/{id}", brokerageNote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(brokerageNote.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.emoluments").value(DEFAULT_EMOLUMENTS.intValue()))
            .andExpect(jsonPath("$.liquidation").value(DEFAULT_LIQUIDATION.intValue()))
            .andExpect(jsonPath("$.otherTaxes").value(DEFAULT_OTHER_TAXES.intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingBrokerageNote() throws Exception {
        // Get the brokerageNote
        restBrokerageNoteMockMvc.perform(get("/api/brokerage-notes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBrokerageNote() throws Exception {
        // Initialize the database
        brokerageNoteRepository.saveAndFlush(brokerageNote);

        int databaseSizeBeforeUpdate = brokerageNoteRepository.findAll().size();

        // Update the brokerageNote
        BrokerageNote updatedBrokerageNote = brokerageNoteRepository.findById(brokerageNote.getId()).get();
        // Disconnect from session so that the updates on updatedBrokerageNote are not directly saved in db
        em.detach(updatedBrokerageNote);
        updatedBrokerageNote
            .number(UPDATED_NUMBER)
            .emoluments(UPDATED_EMOLUMENTS)
            .liquidation(UPDATED_LIQUIDATION)
            .otherTaxes(UPDATED_OTHER_TAXES)
            .value(UPDATED_VALUE);

        restBrokerageNoteMockMvc.perform(put("/api/brokerage-notes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBrokerageNote)))
            .andExpect(status().isOk());

        // Validate the BrokerageNote in the database
        List<BrokerageNote> brokerageNoteList = brokerageNoteRepository.findAll();
        assertThat(brokerageNoteList).hasSize(databaseSizeBeforeUpdate);
        BrokerageNote testBrokerageNote = brokerageNoteList.get(brokerageNoteList.size() - 1);
        assertThat(testBrokerageNote.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testBrokerageNote.getEmoluments()).isEqualTo(UPDATED_EMOLUMENTS);
        assertThat(testBrokerageNote.getLiquidation()).isEqualTo(UPDATED_LIQUIDATION);
        assertThat(testBrokerageNote.getOtherTaxes()).isEqualTo(UPDATED_OTHER_TAXES);
        assertThat(testBrokerageNote.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingBrokerageNote() throws Exception {
        int databaseSizeBeforeUpdate = brokerageNoteRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBrokerageNoteMockMvc.perform(put("/api/brokerage-notes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(brokerageNote)))
            .andExpect(status().isBadRequest());

        // Validate the BrokerageNote in the database
        List<BrokerageNote> brokerageNoteList = brokerageNoteRepository.findAll();
        assertThat(brokerageNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBrokerageNote() throws Exception {
        // Initialize the database
        brokerageNoteRepository.saveAndFlush(brokerageNote);

        int databaseSizeBeforeDelete = brokerageNoteRepository.findAll().size();

        // Delete the brokerageNote
        restBrokerageNoteMockMvc.perform(delete("/api/brokerage-notes/{id}", brokerageNote.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BrokerageNote> brokerageNoteList = brokerageNoteRepository.findAll();
        assertThat(brokerageNoteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
