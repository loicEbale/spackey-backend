package org.spakey.actkey.web.rest;

import org.spakey.actkey.SpakeymsactkeyApp;
import org.spakey.actkey.config.SecurityBeanOverrideConfiguration;
import org.spakey.actkey.domain.InstanceOfKey;
import org.spakey.actkey.repository.InstanceOfKeyRepository;
import org.spakey.actkey.service.InstanceOfKeyService;
import org.spakey.actkey.service.dto.InstanceOfKeyDTO;
import org.spakey.actkey.service.mapper.InstanceOfKeyMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link InstanceOfKeyResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, SpakeymsactkeyApp.class })
@AutoConfigureMockMvc
@WithMockUser
public class InstanceOfKeyResourceIT {

    private static final Long DEFAULT_KEY_INSTANCE_ID = 1L;
    private static final Long UPDATED_KEY_INSTANCE_ID = 2L;

    private static final String DEFAULT_ACTIVATION_KEY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATION_KEY = "BBBBBBBBBB";

    private static final Long DEFAULT_LICENSE_ID = 1L;
    private static final Long UPDATED_LICENSE_ID = 2L;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final LocalDate DEFAULT_ACTIVATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTIVATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private InstanceOfKeyRepository instanceOfKeyRepository;

    @Autowired
    private InstanceOfKeyMapper instanceOfKeyMapper;

    @Autowired
    private InstanceOfKeyService instanceOfKeyService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInstanceOfKeyMockMvc;

    private InstanceOfKey instanceOfKey;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InstanceOfKey createEntity(EntityManager em) {
        InstanceOfKey instanceOfKey = new InstanceOfKey()
            .keyInstanceId(DEFAULT_KEY_INSTANCE_ID)
            .activationKey(DEFAULT_ACTIVATION_KEY)
            .licenseId(DEFAULT_LICENSE_ID)
            .isActive(DEFAULT_IS_ACTIVE)
            .activationDate(DEFAULT_ACTIVATION_DATE);
        return instanceOfKey;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InstanceOfKey createUpdatedEntity(EntityManager em) {
        InstanceOfKey instanceOfKey = new InstanceOfKey()
            .keyInstanceId(UPDATED_KEY_INSTANCE_ID)
            .activationKey(UPDATED_ACTIVATION_KEY)
            .licenseId(UPDATED_LICENSE_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .activationDate(UPDATED_ACTIVATION_DATE);
        return instanceOfKey;
    }

    @BeforeEach
    public void initTest() {
        instanceOfKey = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstanceOfKey() throws Exception {
        int databaseSizeBeforeCreate = instanceOfKeyRepository.findAll().size();
        // Create the InstanceOfKey
        InstanceOfKeyDTO instanceOfKeyDTO = instanceOfKeyMapper.toDto(instanceOfKey);
        restInstanceOfKeyMockMvc.perform(post("/api/instance-of-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instanceOfKeyDTO)))
            .andExpect(status().isCreated());

        // Validate the InstanceOfKey in the database
        List<InstanceOfKey> instanceOfKeyList = instanceOfKeyRepository.findAll();
        assertThat(instanceOfKeyList).hasSize(databaseSizeBeforeCreate + 1);
        InstanceOfKey testInstanceOfKey = instanceOfKeyList.get(instanceOfKeyList.size() - 1);
        assertThat(testInstanceOfKey.getKeyInstanceId()).isEqualTo(DEFAULT_KEY_INSTANCE_ID);
        assertThat(testInstanceOfKey.getActivationKey()).isEqualTo(DEFAULT_ACTIVATION_KEY);
        assertThat(testInstanceOfKey.getLicenseId()).isEqualTo(DEFAULT_LICENSE_ID);
        assertThat(testInstanceOfKey.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testInstanceOfKey.getActivationDate()).isEqualTo(DEFAULT_ACTIVATION_DATE);
    }

    @Test
    @Transactional
    public void createInstanceOfKeyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instanceOfKeyRepository.findAll().size();

        // Create the InstanceOfKey with an existing ID
        instanceOfKey.setId(1L);
        InstanceOfKeyDTO instanceOfKeyDTO = instanceOfKeyMapper.toDto(instanceOfKey);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstanceOfKeyMockMvc.perform(post("/api/instance-of-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instanceOfKeyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InstanceOfKey in the database
        List<InstanceOfKey> instanceOfKeyList = instanceOfKeyRepository.findAll();
        assertThat(instanceOfKeyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInstanceOfKeys() throws Exception {
        // Initialize the database
        instanceOfKeyRepository.saveAndFlush(instanceOfKey);

        // Get all the instanceOfKeyList
        restInstanceOfKeyMockMvc.perform(get("/api/instance-of-keys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instanceOfKey.getId().intValue())))
            .andExpect(jsonPath("$.[*].keyInstanceId").value(hasItem(DEFAULT_KEY_INSTANCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].activationKey").value(hasItem(DEFAULT_ACTIVATION_KEY)))
            .andExpect(jsonPath("$.[*].licenseId").value(hasItem(DEFAULT_LICENSE_ID.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].activationDate").value(hasItem(DEFAULT_ACTIVATION_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getInstanceOfKey() throws Exception {
        // Initialize the database
        instanceOfKeyRepository.saveAndFlush(instanceOfKey);

        // Get the instanceOfKey
        restInstanceOfKeyMockMvc.perform(get("/api/instance-of-keys/{id}", instanceOfKey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(instanceOfKey.getId().intValue()))
            .andExpect(jsonPath("$.keyInstanceId").value(DEFAULT_KEY_INSTANCE_ID.intValue()))
            .andExpect(jsonPath("$.activationKey").value(DEFAULT_ACTIVATION_KEY))
            .andExpect(jsonPath("$.licenseId").value(DEFAULT_LICENSE_ID.intValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.activationDate").value(DEFAULT_ACTIVATION_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingInstanceOfKey() throws Exception {
        // Get the instanceOfKey
        restInstanceOfKeyMockMvc.perform(get("/api/instance-of-keys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstanceOfKey() throws Exception {
        // Initialize the database
        instanceOfKeyRepository.saveAndFlush(instanceOfKey);

        int databaseSizeBeforeUpdate = instanceOfKeyRepository.findAll().size();

        // Update the instanceOfKey
        InstanceOfKey updatedInstanceOfKey = instanceOfKeyRepository.findById(instanceOfKey.getId()).get();
        // Disconnect from session so that the updates on updatedInstanceOfKey are not directly saved in db
        em.detach(updatedInstanceOfKey);
        updatedInstanceOfKey
            .keyInstanceId(UPDATED_KEY_INSTANCE_ID)
            .activationKey(UPDATED_ACTIVATION_KEY)
            .licenseId(UPDATED_LICENSE_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .activationDate(UPDATED_ACTIVATION_DATE);
        InstanceOfKeyDTO instanceOfKeyDTO = instanceOfKeyMapper.toDto(updatedInstanceOfKey);

        restInstanceOfKeyMockMvc.perform(put("/api/instance-of-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instanceOfKeyDTO)))
            .andExpect(status().isOk());

        // Validate the InstanceOfKey in the database
        List<InstanceOfKey> instanceOfKeyList = instanceOfKeyRepository.findAll();
        assertThat(instanceOfKeyList).hasSize(databaseSizeBeforeUpdate);
        InstanceOfKey testInstanceOfKey = instanceOfKeyList.get(instanceOfKeyList.size() - 1);
        assertThat(testInstanceOfKey.getKeyInstanceId()).isEqualTo(UPDATED_KEY_INSTANCE_ID);
        assertThat(testInstanceOfKey.getActivationKey()).isEqualTo(UPDATED_ACTIVATION_KEY);
        assertThat(testInstanceOfKey.getLicenseId()).isEqualTo(UPDATED_LICENSE_ID);
        assertThat(testInstanceOfKey.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testInstanceOfKey.getActivationDate()).isEqualTo(UPDATED_ACTIVATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingInstanceOfKey() throws Exception {
        int databaseSizeBeforeUpdate = instanceOfKeyRepository.findAll().size();

        // Create the InstanceOfKey
        InstanceOfKeyDTO instanceOfKeyDTO = instanceOfKeyMapper.toDto(instanceOfKey);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstanceOfKeyMockMvc.perform(put("/api/instance-of-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instanceOfKeyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InstanceOfKey in the database
        List<InstanceOfKey> instanceOfKeyList = instanceOfKeyRepository.findAll();
        assertThat(instanceOfKeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInstanceOfKey() throws Exception {
        // Initialize the database
        instanceOfKeyRepository.saveAndFlush(instanceOfKey);

        int databaseSizeBeforeDelete = instanceOfKeyRepository.findAll().size();

        // Delete the instanceOfKey
        restInstanceOfKeyMockMvc.perform(delete("/api/instance-of-keys/{id}", instanceOfKey.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InstanceOfKey> instanceOfKeyList = instanceOfKeyRepository.findAll();
        assertThat(instanceOfKeyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
