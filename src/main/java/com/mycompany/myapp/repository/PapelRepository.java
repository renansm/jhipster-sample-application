package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Papel;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Papel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PapelRepository extends JpaRepository<Papel, Long> {
}
