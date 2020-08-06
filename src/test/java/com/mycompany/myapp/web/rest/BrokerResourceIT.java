package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Broker;
import com.mycompany.myapp.repository.BrokerRepository;

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
 * Integration tests for the {@link BrokerResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BrokerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BrokerRepository brokerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBrokerMockMvc;

    private Broker broker;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Broker createEntity(EntityManager em) {
        Broker broker = new Broker()
            .name(DEFAULT_NAME);
        return broker;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Broker createUpdatedEntity(EntityManager em) {
        Broker broker = new Broker()
            .name(UPDATED_NAME);
        return broker;
    }

    @BeforeEach
    public void initTest() {
        broker = createEntity(em);
    }

    @Test
    @Transactional
    public void createBroker() throws Exception {
        int databaseSizeBeforeCreate = brokerRepository.findAll().size();
        // Create the Broker
        restBrokerMockMvc.perform(post("/api/brokers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(broker)))
            .andExpect(status().isCreated());

        // Validate the Broker in the database
        List<Broker> brokerList = brokerRepository.findAll();
        assertThat(brokerList).hasSize(databaseSizeBeforeCreate + 1);
        Broker testBroker = brokerList.get(brokerList.size() - 1);
        assertThat(testBroker.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBrokerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = brokerRepository.findAll().size();

        // Create the Broker with an existing ID
        broker.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBrokerMockMvc.perform(post("/api/brokers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(broker)))
            .andExpect(status().isBadRequest());

        // Validate the Broker in the database
        List<Broker> brokerList = brokerRepository.findAll();
        assertThat(brokerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBrokers() throws Exception {
        // Initialize the database
        brokerRepository.saveAndFlush(broker);

        // Get all the brokerList
        restBrokerMockMvc.perform(get("/api/brokers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(broker.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getBroker() throws Exception {
        // Initialize the database
        brokerRepository.saveAndFlush(broker);

        // Get the broker
        restBrokerMockMvc.perform(get("/api/brokers/{id}", broker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(broker.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingBroker() throws Exception {
        // Get the broker
        restBrokerMockMvc.perform(get("/api/brokers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBroker() throws Exception {
        // Initialize the database
        brokerRepository.saveAndFlush(broker);

        int databaseSizeBeforeUpdate = brokerRepository.findAll().size();

        // Update the broker
        Broker updatedBroker = brokerRepository.findById(broker.getId()).get();
        // Disconnect from session so that the updates on updatedBroker are not directly saved in db
        em.detach(updatedBroker);
        updatedBroker
            .name(UPDATED_NAME);

        restBrokerMockMvc.perform(put("/api/brokers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBroker)))
            .andExpect(status().isOk());

        // Validate the Broker in the database
        List<Broker> brokerList = brokerRepository.findAll();
        assertThat(brokerList).hasSize(databaseSizeBeforeUpdate);
        Broker testBroker = brokerList.get(brokerList.size() - 1);
        assertThat(testBroker.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBroker() throws Exception {
        int databaseSizeBeforeUpdate = brokerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBrokerMockMvc.perform(put("/api/brokers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(broker)))
            .andExpect(status().isBadRequest());

        // Validate the Broker in the database
        List<Broker> brokerList = brokerRepository.findAll();
        assertThat(brokerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBroker() throws Exception {
        // Initialize the database
        brokerRepository.saveAndFlush(broker);

        int databaseSizeBeforeDelete = brokerRepository.findAll().size();

        // Delete the broker
        restBrokerMockMvc.perform(delete("/api/brokers/{id}", broker.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Broker> brokerList = brokerRepository.findAll();
        assertThat(brokerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
