package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Posicao;
import com.mycompany.myapp.repository.PosicaoRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Posicao}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PosicaoResource {

    private final Logger log = LoggerFactory.getLogger(PosicaoResource.class);

    private static final String ENTITY_NAME = "posicao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PosicaoRepository posicaoRepository;

    public PosicaoResource(PosicaoRepository posicaoRepository) {
        this.posicaoRepository = posicaoRepository;
    }

    /**
     * {@code POST  /posicaos} : Create a new posicao.
     *
     * @param posicao the posicao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new posicao, or with status {@code 400 (Bad Request)} if the posicao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/posicaos")
    public ResponseEntity<Posicao> createPosicao(@RequestBody Posicao posicao) throws URISyntaxException {
        log.debug("REST request to save Posicao : {}", posicao);
        if (posicao.getId() != null) {
            throw new BadRequestAlertException("A new posicao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Posicao result = posicaoRepository.save(posicao);
        return ResponseEntity.created(new URI("/api/posicaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /posicaos} : Updates an existing posicao.
     *
     * @param posicao the posicao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated posicao,
     * or with status {@code 400 (Bad Request)} if the posicao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the posicao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/posicaos")
    public ResponseEntity<Posicao> updatePosicao(@RequestBody Posicao posicao) throws URISyntaxException {
        log.debug("REST request to update Posicao : {}", posicao);
        if (posicao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Posicao result = posicaoRepository.save(posicao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, posicao.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /posicaos} : get all the posicaos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of posicaos in body.
     */
    @GetMapping("/posicaos")
    public List<Posicao> getAllPosicaos() {
        log.debug("REST request to get all Posicaos");
        return posicaoRepository.findAll();
    }

    /**
     * {@code GET  /posicaos/:id} : get the "id" posicao.
     *
     * @param id the id of the posicao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the posicao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/posicaos/{id}")
    public ResponseEntity<Posicao> getPosicao(@PathVariable Long id) {
        log.debug("REST request to get Posicao : {}", id);
        Optional<Posicao> posicao = posicaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(posicao);
    }

    /**
     * {@code DELETE  /posicaos/:id} : delete the "id" posicao.
     *
     * @param id the id of the posicao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/posicaos/{id}")
    public ResponseEntity<Void> deletePosicao(@PathVariable Long id) {
        log.debug("REST request to delete Posicao : {}", id);
        posicaoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
