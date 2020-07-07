package org.spakey.actkey.repository;

import org.spakey.actkey.domain.InstanceOfKey;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InstanceOfKey entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstanceOfKeyRepository extends JpaRepository<InstanceOfKey, Long> {
}
