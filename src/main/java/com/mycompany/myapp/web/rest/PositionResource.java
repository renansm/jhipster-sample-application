package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Position;
import com.mycompany.myapp.repository.PositionRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Position}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PositionResource {

    private final Logger log = LoggerFactory.getLogger(PositionResource.class);

    private static final String ENTITY_NAME = "position";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PositionRepository positionRepository;

    public PositionResource(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    /**
     * {@code POST  /positions} : Create a new position.
     *
     * @param position the position to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new position, or with status {@code 400 (Bad Request)} if the position has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/positions")
    public ResponseEntity<Position> createPosition(@RequestBody Position position) throws URISyntaxException {
        log.debug("REST request to save Position : {}", position);
        if (position.getId() != null) {
            throw new BadRequestAlertException("A new position cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Position result = positionRepository.save(position);
        return ResponseEntity.created(new URI("/api/positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /positions} : Updates an existing position.
     *
     * @param position the position to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated position,
     * or with status {@code 400 (Bad Request)} if the position is not valid,
     * or with status {@code 500 (Internal Server Error)} if the position couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/positions")
    public ResponseEntity<Position> updatePosition(@RequestBody Position position) throws URISyntaxException {
        log.debug("REST request to update Position : {}", position);
        if (position.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Position result = positionRepository.save(position);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, position.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /positions} : get all the positions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of positions in body.
     */
    @GetMapping("/positions")
    public List<Position> getAllPositions() {
        log.debug("REST request to get all Positions");
        return positionRepository.findAll();
    }

    /**
     * {@code GET  /positions/:id} : get the "id" position.
     *
     * @param id the id of the position to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the position, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/positions/{id}")
    public ResponseEntity<Position> getPosition(@PathVariable Long id) {
        log.debug("REST request to get Position : {}", id);
        Optional<Position> position = positionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(position);
    }

    /**
     * {@code DELETE  /positions/:id} : delete the "id" position.
     *
     * @param id the id of the position to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/positions/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        log.debug("REST request to delete Position : {}", id);
        positionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
