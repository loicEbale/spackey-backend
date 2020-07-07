package org.spakey.actkey.service;

import org.spakey.actkey.service.dto.InstanceOfKeyDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link org.spakey.actkey.domain.InstanceOfKey}.
 */
public interface InstanceOfKeyService {

    /**
     * Save a instanceOfKey.
     *
     * @param instanceOfKeyDTO the entity to save.
     * @return the persisted entity.
     */
    InstanceOfKeyDTO save(InstanceOfKeyDTO instanceOfKeyDTO);

    /**
     * Get all the instanceOfKeys.
     *
     * @return the list of entities.
     */
    List<InstanceOfKeyDTO> findAll();


    /**
     * Get the "id" instanceOfKey.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InstanceOfKeyDTO> findOne(Long id);

    /**
     * Delete the "id" instanceOfKey.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
