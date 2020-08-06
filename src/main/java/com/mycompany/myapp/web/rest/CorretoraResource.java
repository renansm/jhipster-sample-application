package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Corretora;
import com.mycompany.myapp.repository.CorretoraRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Corretora}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CorretoraResource {

    private final Logger log = LoggerFactory.getLogger(CorretoraResource.class);

    private static final String ENTITY_NAME = "corretora";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CorretoraRepository corretoraRepository;

    public CorretoraResource(CorretoraRepository corretoraRepository) {
        this.corretoraRepository = corretoraRepository;
    }

    /**
     * {@code POST  /corretoras} : Create a new corretora.
     *
     * @param corretora the corretora to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new corretora, or with status {@code 400 (Bad Request)} if the corretora has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/corretoras")
    public ResponseEntity<Corretora> createCorretora(@RequestBody Corretora corretora) throws URISyntaxException {
        log.debug("REST request to save Corretora : {}", corretora);
        if (corretora.getId() != null) {
            throw new BadRequestAlertException("A new corretora cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Corretora result = corretoraRepository.save(corretora);
        return ResponseEntity.created(new URI("/api/corretoras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /corretoras} : Updates an existing corretora.
     *
     * @param corretora the corretora to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated corretora,
     * or with status {@code 400 (Bad Request)} if the corretora is not valid,
     * or with status {@code 500 (Internal Server Error)} if the corretora couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/corretoras")
    public ResponseEntity<Corretora> updateCorretora(@RequestBody Corretora corretora) throws URISyntaxException {
        log.debug("REST request to update Corretora : {}", corretora);
        if (corretora.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Corretora result = corretoraRepository.save(corretora);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, corretora.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /corretoras} : get all the corretoras.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of corretoras in body.
     */
    @GetMapping("/corretoras")
    public List<Corretora> getAllCorretoras() {
        log.debug("REST request to get all Corretoras");
        return corretoraRepository.findAll();
    }

    /**
     * {@code GET  /corretoras/:id} : get the "id" corretora.
     *
     * @param id the id of the corretora to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the corretora, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/corretoras/{id}")
    public ResponseEntity<Corretora> getCorretora(@PathVariable Long id) {
        log.debug("REST request to get Corretora : {}", id);
        Optional<Corretora> corretora = corretoraRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(corretora);
    }

    /**
     * {@code DELETE  /corretoras/:id} : delete the "id" corretora.
     *
     * @param id the id of the corretora to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/corretoras/{id}")
    public ResponseEntity<Void> deleteCorretora(@PathVariable Long id) {
        log.debug("REST request to delete Corretora : {}", id);
        corretoraRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
