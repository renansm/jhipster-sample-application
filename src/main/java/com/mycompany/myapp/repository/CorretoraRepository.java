package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Corretora;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Corretora entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorretoraRepository extends JpaRepository<Corretora, Long> {
}
