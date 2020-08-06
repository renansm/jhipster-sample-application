package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.BrokerageNote;
import com.mycompany.myapp.repository.BrokerageNoteRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.BrokerageNote}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BrokerageNoteResource {

    private final Logger log = LoggerFactory.getLogger(BrokerageNoteResource.class);

    private static final String ENTITY_NAME = "brokerageNote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BrokerageNoteRepository brokerageNoteRepository;

    public BrokerageNoteResource(BrokerageNoteRepository brokerageNoteRepository) {
        this.brokerageNoteRepository = brokerageNoteRepository;
    }

    /**
     * {@code POST  /brokerage-notes} : Create a new brokerageNote.
     *
     * @param brokerageNote the brokerageNote to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new brokerageNote, or with status {@code 400 (Bad Request)} if the brokerageNote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/brokerage-notes")
    public ResponseEntity<BrokerageNote> createBrokerageNote(@RequestBody BrokerageNote brokerageNote) throws URISyntaxException {
        log.debug("REST request to save BrokerageNote : {}", brokerageNote);
        if (brokerageNote.getId() != null) {
            throw new BadRequestAlertException("A new brokerageNote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BrokerageNote result = brokerageNoteRepository.save(brokerageNote);
        return ResponseEntity.created(new URI("/api/brokerage-notes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /brokerage-notes} : Updates an existing brokerageNote.
     *
     * @param brokerageNote the brokerageNote to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated brokerageNote,
     * or with status {@code 400 (Bad Request)} if the brokerageNote is not valid,
     * or with status {@code 500 (Internal Server Error)} if the brokerageNote couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/brokerage-notes")
    public ResponseEntity<BrokerageNote> updateBrokerageNote(@RequestBody BrokerageNote brokerageNote) throws URISyntaxException {
        log.debug("REST request to update BrokerageNote : {}", brokerageNote);
        if (brokerageNote.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BrokerageNote result = brokerageNoteRepository.save(brokerageNote);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, brokerageNote.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /brokerage-notes} : get all the brokerageNotes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of brokerageNotes in body.
     */
    @GetMapping("/brokerage-notes")
    public List<BrokerageNote> getAllBrokerageNotes() {
        log.debug("REST request to get all BrokerageNotes");
        return brokerageNoteRepository.findAll();
    }

    /**
     * {@code GET  /brokerage-notes/:id} : get the "id" brokerageNote.
     *
     * @param id the id of the brokerageNote to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the brokerageNote, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/brokerage-notes/{id}")
    public ResponseEntity<BrokerageNote> getBrokerageNote(@PathVariable Long id) {
        log.debug("REST request to get BrokerageNote : {}", id);
        Optional<BrokerageNote> brokerageNote = brokerageNoteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(brokerageNote);
    }

    /**
     * {@code DELETE  /brokerage-notes/:id} : delete the "id" brokerageNote.
     *
     * @param id the id of the brokerageNote to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/brokerage-notes/{id}")
    public ResponseEntity<Void> deleteBrokerageNote(@PathVariable Long id) {
        log.debug("REST request to delete BrokerageNote : {}", id);
        brokerageNoteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
