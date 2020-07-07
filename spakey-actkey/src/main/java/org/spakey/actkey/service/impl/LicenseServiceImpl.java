package org.spakey.actkey.service.impl;

import org.spakey.actkey.service.LicenseService;
import org.spakey.actkey.domain.License;
import org.spakey.actkey.repository.LicenseRepository;
import org.spakey.actkey.service.dto.LicenseDTO;
import org.spakey.actkey.service.mapper.LicenseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link License}.
 */
@Service
@Transactional
public class LicenseServiceImpl implements LicenseService {

    private final Logger log = LoggerFactory.getLogger(LicenseServiceImpl.class);

    private final LicenseRepository licenseRepository;

    private final LicenseMapper licenseMapper;

    public LicenseServiceImpl(LicenseRepository licenseRepository, LicenseMapper licenseMapper) {
        this.licenseRepository = licenseRepository;
        this.licenseMapper = licenseMapper;
    }

    /**
     * Save a license.
     *
     * @param licenseDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LicenseDTO save(LicenseDTO licenseDTO) {
        log.debug("Request to save License : {}", licenseDTO);
        License license = licenseMapper.toEntity(licenseDTO);
        license = licenseRepository.save(license);
        return licenseMapper.toDto(license);
    }

    /**
     * Get all the licenses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<LicenseDTO> findAll() {
        log.debug("Request to get all Licenses");
        return licenseRepository.findAll().stream()
            .map(licenseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  Get all the licenses where Has is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<LicenseDTO> findAllWhereHasIsNull() {
        log.debug("Request to get all licenses where Has is null");
        return StreamSupport
            .stream(licenseRepository.findAll().spliterator(), false)
            .filter(license -> license.getHas() == null)
            .map(licenseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one license by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LicenseDTO> findOne(Long id) {
        log.debug("Request to get License : {}", id);
        return licenseRepository.findById(id)
            .map(licenseMapper::toDto);
    }

    /**
     * Delete the license by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete License : {}", id);
        licenseRepository.deleteById(id);
    }
}
