package org.spakey.actkey.web.rest;

import org.spakey.actkey.service.InstanceOfKeyService;
import org.spakey.actkey.web.rest.errors.BadRequestAlertException;
import org.spakey.actkey.service.dto.InstanceOfKeyDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.spakey.actkey.domain.InstanceOfKey}.
 */
@RestController
@RequestMapping("/api")
public class InstanceOfKeyResource {

    private final Logger log = LoggerFactory.getLogger(InstanceOfKeyResource.class);

    private static final String ENTITY_NAME = "spakeymsactkeyInstanceOfKey";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstanceOfKeyService instanceOfKeyService;

    public InstanceOfKeyResource(InstanceOfKeyService instanceOfKeyService) {
        this.instanceOfKeyService = instanceOfKeyService;
    }

    /**
     * {@code POST  /instance-of-keys} : Create a new instanceOfKey.
     *
     * @param instanceOfKeyDTO the instanceOfKeyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new instanceOfKeyDTO, or with status {@code 400 (Bad Request)} if the instanceOfKey has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/instance-of-keys")
    public ResponseEntity<InstanceOfKeyDTO> createInstanceOfKey(@Valid @RequestBody InstanceOfKeyDTO instanceOfKeyDTO) throws URISyntaxException {
        log.debug("REST request to save InstanceOfKey : {}", instanceOfKeyDTO);
        if (instanceOfKeyDTO.getId() != null) {
            throw new BadRequestAlertException("A new instanceOfKey cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InstanceOfKeyDTO result = instanceOfKeyService.save(instanceOfKeyDTO);
        return ResponseEntity.created(new URI("/api/instance-of-keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /instance-of-keys} : Updates an existing instanceOfKey.
     *
     * @param instanceOfKeyDTO the instanceOfKeyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instanceOfKeyDTO,
     * or with status {@code 400 (Bad Request)} if the instanceOfKeyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the instanceOfKeyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/instance-of-keys")
    public ResponseEntity<InstanceOfKeyDTO> updateInstanceOfKey(@Valid @RequestBody InstanceOfKeyDTO instanceOfKeyDTO) throws URISyntaxException {
        log.debug("REST request to update InstanceOfKey : {}", instanceOfKeyDTO);
        if (instanceOfKeyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InstanceOfKeyDTO result = instanceOfKeyService.save(instanceOfKeyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, instanceOfKeyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /instance-of-keys} : get all the instanceOfKeys.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of instanceOfKeys in body.
     */
    @GetMapping("/instance-of-keys")
    public List<InstanceOfKeyDTO> getAllInstanceOfKeys() {
        log.debug("REST request to get all InstanceOfKeys");
        return instanceOfKeyService.findAll();
    }

    /**
     * {@code GET  /instance-of-keys/:id} : get the "id" instanceOfKey.
     *
     * @param id the id of the instanceOfKeyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the instanceOfKeyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/instance-of-keys/{id}")
    public ResponseEntity<InstanceOfKeyDTO> getInstanceOfKey(@PathVariable Long id) {
        log.debug("REST request to get InstanceOfKey : {}", id);
        Optional<InstanceOfKeyDTO> instanceOfKeyDTO = instanceOfKeyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(instanceOfKeyDTO);
    }

    /**
     * {@code DELETE  /instance-of-keys/:id} : delete the "id" instanceOfKey.
     *
     * @param id the id of the instanceOfKeyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/instance-of-keys/{id}")
    public ResponseEntity<Void> deleteInstanceOfKey(@PathVariable Long id) {
        log.debug("REST request to delete InstanceOfKey : {}", id);
        instanceOfKeyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
