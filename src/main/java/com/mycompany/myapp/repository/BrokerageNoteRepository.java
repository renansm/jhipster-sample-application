package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BrokerageNote;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BrokerageNote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BrokerageNoteRepository extends JpaRepository<BrokerageNote, Long> {
}
