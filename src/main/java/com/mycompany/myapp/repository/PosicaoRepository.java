package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Posicao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Posicao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PosicaoRepository extends JpaRepository<Posicao, Long> {
}
