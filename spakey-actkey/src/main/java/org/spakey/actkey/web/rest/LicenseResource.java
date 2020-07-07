package org.spakey.actkey.web.rest;

import org.spakey.actkey.service.LicenseService;
import org.spakey.actkey.web.rest.errors.BadRequestAlertException;
import org.spakey.actkey.service.dto.LicenseDTO;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link org.spakey.actkey.domain.License}.
 */
@RestController
@RequestMapping("/api")
public class LicenseResource {

    private final Logger log = LoggerFactory.getLogger(LicenseResource.class);

    private static final String ENTITY_NAME = "spakeymsactkeyLicense";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LicenseService licenseService;

    public LicenseResource(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    /**
     * {@code POST  /licenses} : Create a new license.
     *
     * @param licenseDTO the licenseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new licenseDTO, or with status {@code 400 (Bad Request)} if the license has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/licenses")
    public ResponseEntity<LicenseDTO> createLicense(@Valid @RequestBody LicenseDTO licenseDTO) throws URISyntaxException {
        log.debug("REST request to save License : {}", licenseDTO);
        if (licenseDTO.getId() != null) {
            throw new BadRequestAlertException("A new license cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LicenseDTO result = licenseService.save(licenseDTO);
        return ResponseEntity.created(new URI("/api/licenses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /licenses} : Updates an existing license.
     *
     * @param licenseDTO the licenseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated licenseDTO,
     * or with status {@code 400 (Bad Request)} if the licenseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the licenseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/licenses")
    public ResponseEntity<LicenseDTO> updateLicense(@Valid @RequestBody LicenseDTO licenseDTO) throws URISyntaxException {
        log.debug("REST request to update License : {}", licenseDTO);
        if (licenseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LicenseDTO result = licenseService.save(licenseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, licenseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /licenses} : get all the licenses.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of licenses in body.
     */
    @GetMapping("/licenses")
    public List<LicenseDTO> getAllLicenses(@RequestParam(required = false) String filter) {
        if ("has-is-null".equals(filter)) {
            log.debug("REST request to get all Licenses where has is null");
            return licenseService.findAllWhereHasIsNull();
        }
        log.debug("REST request to get all Licenses");
        return licenseService.findAll();
    }

    /**
     * {@code GET  /licenses/:id} : get the "id" license.
     *
     * @param id the id of the licenseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the licenseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/licenses/{id}")
    public ResponseEntity<LicenseDTO> getLicense(@PathVariable Long id) {
        log.debug("REST request to get License : {}", id);
        Optional<LicenseDTO> licenseDTO = licenseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(licenseDTO);
    }

    /**
     * {@code DELETE  /licenses/:id} : delete the "id" license.
     *
     * @param id the id of the licenseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/licenses/{id}")
    public ResponseEntity<Void> deleteLicense(@PathVariable Long id) {
        log.debug("REST request to delete License : {}", id);
        licenseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
