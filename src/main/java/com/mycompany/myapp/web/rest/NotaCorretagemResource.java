package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.NotaCorretagem;
import com.mycompany.myapp.repository.NotaCorretagemRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.NotaCorretagem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NotaCorretagemResource {

    private final Logger log = LoggerFactory.getLogger(NotaCorretagemResource.class);

    private static final String ENTITY_NAME = "notaCorretagem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotaCorretagemRepository notaCorretagemRepository;

    public NotaCorretagemResource(NotaCorretagemRepository notaCorretagemRepository) {
        this.notaCorretagemRepository = notaCorretagemRepository;
    }

    /**
     * {@code POST  /nota-corretagems} : Create a new notaCorretagem.
     *
     * @param notaCorretagem the notaCorretagem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notaCorretagem, or with status {@code 400 (Bad Request)} if the notaCorretagem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nota-corretagems")
    public ResponseEntity<NotaCorretagem> createNotaCorretagem(@RequestBody NotaCorretagem notaCorretagem) throws URISyntaxException {
        log.debug("REST request to save NotaCorretagem : {}", notaCorretagem);
        if (notaCorretagem.getId() != null) {
            throw new BadRequestAlertException("A new notaCorretagem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotaCorretagem result = notaCorretagemRepository.save(notaCorretagem);
        return ResponseEntity.created(new URI("/api/nota-corretagems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nota-corretagems} : Updates an existing notaCorretagem.
     *
     * @param notaCorretagem the notaCorretagem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notaCorretagem,
     * or with status {@code 400 (Bad Request)} if the notaCorretagem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notaCorretagem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nota-corretagems")
    public ResponseEntity<NotaCorretagem> updateNotaCorretagem(@RequestBody NotaCorretagem notaCorretagem) throws URISyntaxException {
        log.debug("REST request to update NotaCorretagem : {}", notaCorretagem);
        if (notaCorretagem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NotaCorretagem result = notaCorretagemRepository.save(notaCorretagem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, notaCorretagem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nota-corretagems} : get all the notaCorretagems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notaCorretagems in body.
     */
    @GetMapping("/nota-corretagems")
    public List<NotaCorretagem> getAllNotaCorretagems() {
        log.debug("REST request to get all NotaCorretagems");
        return notaCorretagemRepository.findAll();
    }

    /**
     * {@code GET  /nota-corretagems/:id} : get the "id" notaCorretagem.
     *
     * @param id the id of the notaCorretagem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notaCorretagem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nota-corretagems/{id}")
    public ResponseEntity<NotaCorretagem> getNotaCorretagem(@PathVariable Long id) {
        log.debug("REST request to get NotaCorretagem : {}", id);
        Optional<NotaCorretagem> notaCorretagem = notaCorretagemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(notaCorretagem);
    }

    /**
     * {@code DELETE  /nota-corretagems/:id} : delete the "id" notaCorretagem.
     *
     * @param id the id of the notaCorretagem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nota-corretagems/{id}")
    public ResponseEntity<Void> deleteNotaCorretagem(@PathVariable Long id) {
        log.debug("REST request to delete NotaCorretagem : {}", id);
        notaCorretagemRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
