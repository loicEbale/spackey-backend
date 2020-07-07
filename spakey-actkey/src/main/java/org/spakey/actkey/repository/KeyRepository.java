package org.spakey.actkey.repository;

import java.util.Optional;

import org.spakey.actkey.domain.Key;
import org.spakey.actkey.service.dto.KeyDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Key entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KeyRepository extends JpaRepository<Key, Long> {
	
	Optional<KeyDTO> findByActivationKey(String activationKey);
	
}
