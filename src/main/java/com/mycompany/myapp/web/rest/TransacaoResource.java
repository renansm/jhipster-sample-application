package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Transacao;
import com.mycompany.myapp.repository.TransacaoRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Transacao}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TransacaoResource {

    private final Logger log = LoggerFactory.getLogger(TransacaoResource.class);

    private static final String ENTITY_NAME = "transacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransacaoRepository transacaoRepository;

    public TransacaoResource(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    /**
     * {@code POST  /transacaos} : Create a new transacao.
     *
     * @param transacao the transacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transacao, or with status {@code 400 (Bad Request)} if the transacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transacaos")
    public ResponseEntity<Transacao> createTransacao(@RequestBody Transacao transacao) throws URISyntaxException {
        log.debug("REST request to save Transacao : {}", transacao);
        if (transacao.getId() != null) {
            throw new BadRequestAlertException("A new transacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Transacao result = transacaoRepository.save(transacao);
        return ResponseEntity.created(new URI("/api/transacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transacaos} : Updates an existing transacao.
     *
     * @param transacao the transacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transacao,
     * or with status {@code 400 (Bad Request)} if the transacao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transacaos")
    public ResponseEntity<Transacao> updateTransacao(@RequestBody Transacao transacao) throws URISyntaxException {
        log.debug("REST request to update Transacao : {}", transacao);
        if (transacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Transacao result = transacaoRepository.save(transacao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transacao.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transacaos} : get all the transacaos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transacaos in body.
     */
    @GetMapping("/transacaos")
    public List<Transacao> getAllTransacaos() {
        log.debug("REST request to get all Transacaos");
        return transacaoRepository.findAll();
    }

    /**
     * {@code GET  /transacaos/:id} : get the "id" transacao.
     *
     * @param id the id of the transacao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transacao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transacaos/{id}")
    public ResponseEntity<Transacao> getTransacao(@PathVariable Long id) {
        log.debug("REST request to get Transacao : {}", id);
        Optional<Transacao> transacao = transacaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(transacao);
    }

    /**
     * {@code DELETE  /transacaos/:id} : delete the "id" transacao.
     *
     * @param id the id of the transacao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transacaos/{id}")
    public ResponseEntity<Void> deleteTransacao(@PathVariable Long id) {
        log.debug("REST request to delete Transacao : {}", id);
        transacaoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
