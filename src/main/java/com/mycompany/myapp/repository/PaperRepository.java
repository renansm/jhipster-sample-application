package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Paper;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Paper entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaperRepository extends JpaRepository<Paper, Long> {
}
