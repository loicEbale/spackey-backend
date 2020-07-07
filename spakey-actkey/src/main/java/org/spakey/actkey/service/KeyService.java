package org.spakey.actkey.service;

import org.spakey.actkey.service.dto.ActivationRequestDTO;
import org.spakey.actkey.service.dto.KeyDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link org.spakey.actkey.domain.Key}.
 */
public interface KeyService {

    /**
     * Save a key.
     *
     * @param keyDTO the entity to save.
     * @return the persisted entity.
     */
    KeyDTO save(KeyDTO keyDTO);

    /**
     * Get all the keys.
     *
     * @return the list of entities.
     */
    List<KeyDTO> findAll();


    /**
     * Get the "id" key.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KeyDTO> findOne(Long id);

    /**
     * Delete the "id" key.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    
    KeyDTO activationRequestToKeyDTO(ActivationRequestDTO activationRequestDTO, String gKey);
    
    /**
     * Generate an activation key
     */
    String generateActivationKey();
    
    
}
