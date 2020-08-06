package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Papel;
import com.mycompany.myapp.repository.PapelRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Papel}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PapelResource {

    private final Logger log = LoggerFactory.getLogger(PapelResource.class);

    private static final String ENTITY_NAME = "papel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PapelRepository papelRepository;

    public PapelResource(PapelRepository papelRepository) {
        this.papelRepository = papelRepository;
    }

    /**
     * {@code POST  /papels} : Create a new papel.
     *
     * @param papel the papel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new papel, or with status {@code 400 (Bad Request)} if the papel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/papels")
    public ResponseEntity<Papel> createPapel(@RequestBody Papel papel) throws URISyntaxException {
        log.debug("REST request to save Papel : {}", papel);
        if (papel.getId() != null) {
            throw new BadRequestAlertException("A new papel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Papel result = papelRepository.save(papel);
        return ResponseEntity.created(new URI("/api/papels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /papels} : Updates an existing papel.
     *
     * @param papel the papel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated papel,
     * or with status {@code 400 (Bad Request)} if the papel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the papel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/papels")
    public ResponseEntity<Papel> updatePapel(@RequestBody Papel papel) throws URISyntaxException {
        log.debug("REST request to update Papel : {}", papel);
        if (papel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Papel result = papelRepository.save(papel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, papel.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /papels} : get all the papels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of papels in body.
     */
    @GetMapping("/papels")
    public List<Papel> getAllPapels() {
        log.debug("REST request to get all Papels");
        return papelRepository.findAll();
    }

    /**
     * {@code GET  /papels/:id} : get the "id" papel.
     *
     * @param id the id of the papel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the papel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/papels/{id}")
    public ResponseEntity<Papel> getPapel(@PathVariable Long id) {
        log.debug("REST request to get Papel : {}", id);
        Optional<Papel> papel = papelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(papel);
    }

    /**
     * {@code DELETE  /papels/:id} : delete the "id" papel.
     *
     * @param id the id of the papel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/papels/{id}")
    public ResponseEntity<Void> deletePapel(@PathVariable Long id) {
        log.debug("REST request to delete Papel : {}", id);
        papelRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
