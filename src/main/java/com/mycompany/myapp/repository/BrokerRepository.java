package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Broker;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Broker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BrokerRepository extends JpaRepository<Broker, Long> {
}
