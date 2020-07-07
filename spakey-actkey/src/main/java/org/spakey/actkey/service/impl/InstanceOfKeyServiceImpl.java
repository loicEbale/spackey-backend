package org.spakey.actkey.service.impl;

import org.spakey.actkey.service.InstanceOfKeyService;
import org.spakey.actkey.domain.InstanceOfKey;
import org.spakey.actkey.repository.InstanceOfKeyRepository;
import org.spakey.actkey.service.dto.InstanceOfKeyDTO;
import org.spakey.actkey.service.mapper.InstanceOfKeyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link InstanceOfKey}.
 */
@Service
@Transactional
public class InstanceOfKeyServiceImpl implements InstanceOfKeyService {

    private final Logger log = LoggerFactory.getLogger(InstanceOfKeyServiceImpl.class);

    private final InstanceOfKeyRepository instanceOfKeyRepository;

    private final InstanceOfKeyMapper instanceOfKeyMapper;

    public InstanceOfKeyServiceImpl(InstanceOfKeyRepository instanceOfKeyRepository, InstanceOfKeyMapper instanceOfKeyMapper) {
        this.instanceOfKeyRepository = instanceOfKeyRepository;
        this.instanceOfKeyMapper = instanceOfKeyMapper;
    }

    /**
     * Save a instanceOfKey.
     *
     * @param instanceOfKeyDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InstanceOfKeyDTO save(InstanceOfKeyDTO instanceOfKeyDTO) {
        log.debug("Request to save InstanceOfKey : {}", instanceOfKeyDTO);
        InstanceOfKey instanceOfKey = instanceOfKeyMapper.toEntity(instanceOfKeyDTO);
        instanceOfKey = instanceOfKeyRepository.save(instanceOfKey);
        return instanceOfKeyMapper.toDto(instanceOfKey);
    }

    /**
     * Get all the instanceOfKeys.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InstanceOfKeyDTO> findAll() {
        log.debug("Request to get all InstanceOfKeys");
        return instanceOfKeyRepository.findAll().stream()
            .map(instanceOfKeyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one instanceOfKey by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InstanceOfKeyDTO> findOne(Long id) {
        log.debug("Request to get InstanceOfKey : {}", id);
        return instanceOfKeyRepository.findById(id)
            .map(instanceOfKeyMapper::toDto);
    }

    /**
     * Delete the instanceOfKey by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InstanceOfKey : {}", id);
        instanceOfKeyRepository.deleteById(id);
    }
}
