package org.spakey.actkey.service.impl;

import org.spakey.actkey.service.KeyService;
import org.spakey.actkey.domain.Key;
import org.spakey.actkey.repository.KeyRepository;
import org.spakey.actkey.service.dto.ActivationRequestDTO;
import org.spakey.actkey.service.dto.KeyDTO;
import org.spakey.actkey.service.mapper.KeyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.management.openmbean.KeyAlreadyExistsException;

/**
 * Service Implementation for managing {@link Key}.
 */
@Service
@Transactional
public class KeyServiceImpl implements KeyService {

    private final Logger log = LoggerFactory.getLogger(KeyServiceImpl.class);

    private final KeyRepository keyRepository;

    private final KeyMapper keyMapper;

    public KeyServiceImpl(KeyRepository keyRepository, KeyMapper keyMapper) {
        this.keyRepository = keyRepository;
        this.keyMapper = keyMapper;
    }

    /**
     * Save a key.
     *
     * @param keyDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public KeyDTO save(KeyDTO keyDTO) {
        log.debug("Request to save Key : {}", keyDTO);
        Key key = keyMapper.toEntity(keyDTO);
        key = keyRepository.save(key);
        return keyMapper.toDto(key);
    }

    /**
     * Get all the keys.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<KeyDTO> findAll() {
        log.debug("Request to get all Keys");
        return keyRepository.findAll().stream()
            .map(keyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one key by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<KeyDTO> findOne(Long id) {
        log.debug("Request to get Key : {}", id);
        return keyRepository.findById(id)
            .map(keyMapper::toDto);
    }

    /**
     * Delete the key by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Key : {}", id);
        keyRepository.deleteById(id);
    }

    
	@Override
	public String generateActivationKey() {
		final String characters = "QSDFGHNBVCXWAZERTYMLKJPU123456789";
		
		Set<String> setKey = new HashSet<String>();
		
		String randomKey = "";
		String key = "";
		
		int length = 5;
		
		SecureRandom rand = new SecureRandom();
		
		for (int t = 0; t < length-1; t++) {
			
			char[] text = new char[length];
			
			for (int i = 0; i < length; i++) {
				text[i] = characters.charAt(rand.nextInt(characters.length()));
			}
			
			for (int i = 0; i < text.length; i++) {
				randomKey += text[i];
			}
			
			setKey.add(randomKey);
			randomKey = "";
		}
		
		key = String.join("-", setKey);
//		Map<String, String> response = new HashMap<>();
//		
//		response.put("activation_key", key);
		
		return key;
	}

	@Override
	public KeyDTO activationRequestToKeyDTO(ActivationRequestDTO activationRequest, String gKey) {
		Optional<KeyDTO> testKey = keyRepository.findByActivationKey(gKey);
		
        if(testKey.isPresent() == true) {
        	throw new KeyAlreadyExistsException(gKey + "Already Exists !!!");
        }
		KeyDTO keyDTO = new KeyDTO();
        keyDTO.setActivationKey(gKey);
        keyDTO.setActivationNumber(activationRequest.getActivationNumber());
        keyDTO.setUserId(activationRequest.getUserId());
        keyDTO.setProductId(activationRequest.getProductId());		
        return keyDTO;
	}
}
