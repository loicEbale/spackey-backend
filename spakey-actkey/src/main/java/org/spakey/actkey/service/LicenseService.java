package org.spakey.actkey.service;

import org.spakey.actkey.service.dto.LicenseDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link org.spakey.actkey.domain.License}.
 */
public interface LicenseService {

    /**
     * Save a license.
     *
     * @param licenseDTO the entity to save.
     * @return the persisted entity.
     */
    LicenseDTO save(LicenseDTO licenseDTO);

    /**
     * Get all the licenses.
     *
     * @return the list of entities.
     */
    List<LicenseDTO> findAll();
    /**
     * Get all the LicenseDTO where Has is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<LicenseDTO> findAllWhereHasIsNull();


    /**
     * Get the "id" license.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LicenseDTO> findOne(Long id);

    /**
     * Delete the "id" license.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
