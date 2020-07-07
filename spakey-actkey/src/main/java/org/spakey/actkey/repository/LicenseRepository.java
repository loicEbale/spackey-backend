package org.spakey.actkey.repository;

import org.spakey.actkey.domain.License;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the License entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {
}
