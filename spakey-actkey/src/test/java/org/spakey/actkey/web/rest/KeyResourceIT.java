package org.spakey.actkey.web.rest;

import org.spakey.actkey.SpakeymsactkeyApp;
import org.spakey.actkey.config.SecurityBeanOverrideConfiguration;
import org.spakey.actkey.domain.Key;
import org.spakey.actkey.repository.KeyRepository;
import org.spakey.actkey.service.KeyService;
import org.spakey.actkey.service.dto.KeyDTO;
import org.spakey.actkey.service.mapper.KeyMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link KeyResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, SpakeymsactkeyApp.class })
@AutoConfigureMockMvc
@WithMockUser
public class KeyResourceIT {

    private static final String DEFAULT_ACTIVATION_KEY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATION_KEY = "BBBBBBBBBB";

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final Integer DEFAULT_ACTIVATION_NUMBER = 1;
    private static final Integer UPDATED_ACTIVATION_NUMBER = 2;

    @Autowired
    private KeyRepository keyRepository;

    @Autowired
    private KeyMapper keyMapper;

    @Autowired
    private KeyService keyService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKeyMockMvc;

    private Key key;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Key createEntity(EntityManager em) {
        Key key = new Key()
            .activationKey(DEFAULT_ACTIVATION_KEY)
            .userId(DEFAULT_USER_ID)
            .productId(DEFAULT_PRODUCT_ID)
            .activationNumber(DEFAULT_ACTIVATION_NUMBER);
        return key;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Key createUpdatedEntity(EntityManager em) {
        Key key = new Key()
            .activationKey(UPDATED_ACTIVATION_KEY)
            .userId(UPDATED_USER_ID)
            .productId(UPDATED_PRODUCT_ID)
            .activationNumber(UPDATED_ACTIVATION_NUMBER);
        return key;
    }

    @BeforeEach
    public void initTest() {
        key = createEntity(em);
    }

    @Test
    @Transactional
    public void createKey() throws Exception {
        int databaseSizeBeforeCreate = keyRepository.findAll().size();
        // Create the Key
        KeyDTO keyDTO = keyMapper.toDto(key);
        restKeyMockMvc.perform(post("/api/keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyDTO)))
            .andExpect(status().isCreated());

        // Validate the Key in the database
        List<Key> keyList = keyRepository.findAll();
        assertThat(keyList).hasSize(databaseSizeBeforeCreate + 1);
        Key testKey = keyList.get(keyList.size() - 1);
        assertThat(testKey.getActivationKey()).isEqualTo(DEFAULT_ACTIVATION_KEY);
        assertThat(testKey.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testKey.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testKey.getActivationNumber()).isEqualTo(DEFAULT_ACTIVATION_NUMBER);
    }

    @Test
    @Transactional
    public void createKeyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = keyRepository.findAll().size();

        // Create the Key with an existing ID
        key.setId(1L);
        KeyDTO keyDTO = keyMapper.toDto(key);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKeyMockMvc.perform(post("/api/keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Key in the database
        List<Key> keyList = keyRepository.findAll();
        assertThat(keyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkActivationKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = keyRepository.findAll().size();
        // set the field null
        key.setActivationKey(null);

        // Create the Key, which fails.
        KeyDTO keyDTO = keyMapper.toDto(key);


        restKeyMockMvc.perform(post("/api/keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyDTO)))
            .andExpect(status().isBadRequest());

        List<Key> keyList = keyRepository.findAll();
        assertThat(keyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKeys() throws Exception {
        // Initialize the database
        keyRepository.saveAndFlush(key);

        // Get all the keyList
        restKeyMockMvc.perform(get("/api/keys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(key.getId().intValue())))
            .andExpect(jsonPath("$.[*].activationKey").value(hasItem(DEFAULT_ACTIVATION_KEY)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].activationNumber").value(hasItem(DEFAULT_ACTIVATION_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getKey() throws Exception {
        // Initialize the database
        keyRepository.saveAndFlush(key);

        // Get the key
        restKeyMockMvc.perform(get("/api/keys/{id}", key.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(key.getId().intValue()))
            .andExpect(jsonPath("$.activationKey").value(DEFAULT_ACTIVATION_KEY))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.activationNumber").value(DEFAULT_ACTIVATION_NUMBER));
    }
    @Test
    @Transactional
    public void getNonExistingKey() throws Exception {
        // Get the key
        restKeyMockMvc.perform(get("/api/keys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKey() throws Exception {
        // Initialize the database
        keyRepository.saveAndFlush(key);

        int databaseSizeBeforeUpdate = keyRepository.findAll().size();

        // Update the key
        Key updatedKey = keyRepository.findById(key.getId()).get();
        // Disconnect from session so that the updates on updatedKey are not directly saved in db
        em.detach(updatedKey);
        updatedKey
            .activationKey(UPDATED_ACTIVATION_KEY)
            .userId(UPDATED_USER_ID)
            .productId(UPDATED_PRODUCT_ID)
            .activationNumber(UPDATED_ACTIVATION_NUMBER);
        KeyDTO keyDTO = keyMapper.toDto(updatedKey);

        restKeyMockMvc.perform(put("/api/keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyDTO)))
            .andExpect(status().isOk());

        // Validate the Key in the database
        List<Key> keyList = keyRepository.findAll();
        assertThat(keyList).hasSize(databaseSizeBeforeUpdate);
        Key testKey = keyList.get(keyList.size() - 1);
        assertThat(testKey.getActivationKey()).isEqualTo(UPDATED_ACTIVATION_KEY);
        assertThat(testKey.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testKey.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testKey.getActivationNumber()).isEqualTo(UPDATED_ACTIVATION_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingKey() throws Exception {
        int databaseSizeBeforeUpdate = keyRepository.findAll().size();

        // Create the Key
        KeyDTO keyDTO = keyMapper.toDto(key);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKeyMockMvc.perform(put("/api/keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Key in the database
        List<Key> keyList = keyRepository.findAll();
        assertThat(keyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKey() throws Exception {
        // Initialize the database
        keyRepository.saveAndFlush(key);

        int databaseSizeBeforeDelete = keyRepository.findAll().size();

        // Delete the key
        restKeyMockMvc.perform(delete("/api/keys/{id}", key.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Key> keyList = keyRepository.findAll();
        assertThat(keyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
