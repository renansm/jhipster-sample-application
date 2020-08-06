package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Transacao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Transacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
