package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.NotaCorretagem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the NotaCorretagem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotaCorretagemRepository extends JpaRepository<NotaCorretagem, Long> {
}
