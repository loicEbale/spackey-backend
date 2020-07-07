package org.spakey.actkey.web.rest;

import org.spakey.actkey.repository.KeyRepository;
import org.spakey.actkey.service.KeyService;
import org.spakey.actkey.web.rest.errors.BadRequestAlertException;
import org.spakey.actkey.service.dto.ActivationRequestDTO;
import org.spakey.actkey.service.dto.KeyDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.spakey.actkey.domain.Key}.
 */
@RestController
@RequestMapping("/api")
public class KeyResource {

    private final Logger log = LoggerFactory.getLogger(KeyResource.class);

    private static final String ENTITY_NAME = "spakeymsactkeyKey";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KeyService keyService;
    
    private final KeyRepository keyRepository;

    public KeyResource(KeyService keyService, KeyRepository keyRepository) {
		this.keyRepository = keyRepository;
		this.keyService = keyService;
    }

    /**
     * {@code POST  /keys} : Create a new key.
     *
     * @param keyDTO the keyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new keyDTO, or with status {@code 400 (Bad Request)} if the key has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/keys")
    public ResponseEntity<KeyDTO> createKey(@Valid @RequestBody ActivationRequestDTO activationRequest) throws URISyntaxException {
        log.debug("REST request to save Key : {}", activationRequest);
//        if (keyDTO.getId() != null) {
//            throw new BadRequestAlertException("A new key cannot already have an ID", ENTITY_NAME, "idexists");
//        }
        String gKey = keyService.generateActivationKey();
        
        KeyDTO keyDTO = keyService.activationRequestToKeyDTO(activationRequest, gKey);
        KeyDTO result = keyService.save(keyDTO);
        return ResponseEntity.created(new URI("/api/keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /keys} : Updates an existing key.
     *
     * @param keyDTO the keyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated keyDTO,
     * or with status {@code 400 (Bad Request)} if the keyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the keyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/keys")
    public ResponseEntity<KeyDTO> updateKey(@Valid @RequestBody KeyDTO keyDTO) throws URISyntaxException {
        log.debug("REST request to update Key : {}", keyDTO);
        if (keyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KeyDTO result = keyService.save(keyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, keyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /keys} : get all the keys.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of keys in body.
     */
    @GetMapping("/keys")
    public List<KeyDTO> getAllKeys() {
        log.debug("REST request to get all Keys");
        return keyService.findAll();
    }

    /**
     * {@code GET  /keys/:id} : get the "id" key.
     *
     * @param id the id of the keyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the keyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/keys/{id}")
    public ResponseEntity<KeyDTO> getKey(@PathVariable Long id) {
        log.debug("REST request to get Key : {}", id);
        Optional<KeyDTO> keyDTO = keyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(keyDTO);
    }

    /**
     * {@code GET  /keys/:key} : get an "activation_key".
     *
     * @param key the activation_key of the keyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the keyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/keys")
    public ResponseEntity<KeyDTO> geActivationKey(@Param(value = "key") String key) {
    	log.debug("REST request to key : {}", key);
    	Optional<KeyDTO> keyDTO = keyRepository.findByActivationKey(key);
    	return ResponseUtil.wrapOrNotFound(keyDTO);
    }

    
    /**
     * {@code DELETE  /keys/:id} : delete the "id" key.
     *
     * @param id the id of the keyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/keys/{id}")
    public ResponseEntity<Void> deleteKey(@PathVariable Long id) {
        log.debug("REST request to delete Key : {}", id);
        keyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
    
}
