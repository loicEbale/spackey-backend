package org.spakey.actkey.web.rest;

import org.spakey.actkey.SpakeymsactkeyApp;
import org.spakey.actkey.config.SecurityBeanOverrideConfiguration;
import org.spakey.actkey.domain.License;
import org.spakey.actkey.repository.LicenseRepository;
import org.spakey.actkey.service.LicenseService;
import org.spakey.actkey.service.dto.LicenseDTO;
import org.spakey.actkey.service.mapper.LicenseMapper;

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
 * Integration tests for the {@link LicenseResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, SpakeymsactkeyApp.class })
@AutoConfigureMockMvc
@WithMockUser
public class LicenseResourceIT {

    private static final Long DEFAULT_LICENSE_ID = 1L;
    private static final Long UPDATED_LICENSE_ID = 2L;

    private static final Long DEFAULT_KEY_INSTANCE_ID = 1L;
    private static final Long UPDATED_KEY_INSTANCE_ID = 2L;

    private static final Integer DEFAULT_VALIDITY = 1;
    private static final Integer UPDATED_VALIDITY = 2;

    private static final LocalDate DEFAULT_DATE_BEGIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_BEGIN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_END = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_EXTENSIONS_NUMBER = 1;
    private static final Integer UPDATED_EXTENSIONS_NUMBER = 2;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private LicenseMapper licenseMapper;

    @Autowired
    private LicenseService licenseService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLicenseMockMvc;

    private License license;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static License createEntity(EntityManager em) {
        License license = new License()
            .licenseId(DEFAULT_LICENSE_ID)
            .keyInstanceId(DEFAULT_KEY_INSTANCE_ID)
            .validity(DEFAULT_VALIDITY)
            .dateBegin(DEFAULT_DATE_BEGIN)
            .dateEnd(DEFAULT_DATE_END)
            .extensionsNumber(DEFAULT_EXTENSIONS_NUMBER);
        return license;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static License createUpdatedEntity(EntityManager em) {
        License license = new License()
            .licenseId(UPDATED_LICENSE_ID)
            .keyInstanceId(UPDATED_KEY_INSTANCE_ID)
            .validity(UPDATED_VALIDITY)
            .dateBegin(UPDATED_DATE_BEGIN)
            .dateEnd(UPDATED_DATE_END)
            .extensionsNumber(UPDATED_EXTENSIONS_NUMBER);
        return license;
    }

    @BeforeEach
    public void initTest() {
        license = createEntity(em);
    }

    @Test
    @Transactional
    public void createLicense() throws Exception {
        int databaseSizeBeforeCreate = licenseRepository.findAll().size();
        // Create the License
        LicenseDTO licenseDTO = licenseMapper.toDto(license);
        restLicenseMockMvc.perform(post("/api/licenses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(licenseDTO)))
            .andExpect(status().isCreated());

        // Validate the License in the database
        List<License> licenseList = licenseRepository.findAll();
        assertThat(licenseList).hasSize(databaseSizeBeforeCreate + 1);
        License testLicense = licenseList.get(licenseList.size() - 1);
        assertThat(testLicense.getLicenseId()).isEqualTo(DEFAULT_LICENSE_ID);
        assertThat(testLicense.getKeyInstanceId()).isEqualTo(DEFAULT_KEY_INSTANCE_ID);
        assertThat(testLicense.getValidity()).isEqualTo(DEFAULT_VALIDITY);
        assertThat(testLicense.getDateBegin()).isEqualTo(DEFAULT_DATE_BEGIN);
        assertThat(testLicense.getDateEnd()).isEqualTo(DEFAULT_DATE_END);
        assertThat(testLicense.getExtensionsNumber()).isEqualTo(DEFAULT_EXTENSIONS_NUMBER);
    }

    @Test
    @Transactional
    public void createLicenseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = licenseRepository.findAll().size();

        // Create the License with an existing ID
        license.setId(1L);
        LicenseDTO licenseDTO = licenseMapper.toDto(license);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLicenseMockMvc.perform(post("/api/licenses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(licenseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the License in the database
        List<License> licenseList = licenseRepository.findAll();
        assertThat(licenseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLicenses() throws Exception {
        // Initialize the database
        licenseRepository.saveAndFlush(license);

        // Get all the licenseList
        restLicenseMockMvc.perform(get("/api/licenses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(license.getId().intValue())))
            .andExpect(jsonPath("$.[*].licenseId").value(hasItem(DEFAULT_LICENSE_ID.intValue())))
            .andExpect(jsonPath("$.[*].keyInstanceId").value(hasItem(DEFAULT_KEY_INSTANCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].validity").value(hasItem(DEFAULT_VALIDITY)))
            .andExpect(jsonPath("$.[*].dateBegin").value(hasItem(DEFAULT_DATE_BEGIN.toString())))
            .andExpect(jsonPath("$.[*].dateEnd").value(hasItem(DEFAULT_DATE_END.toString())))
            .andExpect(jsonPath("$.[*].extensionsNumber").value(hasItem(DEFAULT_EXTENSIONS_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getLicense() throws Exception {
        // Initialize the database
        licenseRepository.saveAndFlush(license);

        // Get the license
        restLicenseMockMvc.perform(get("/api/licenses/{id}", license.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(license.getId().intValue()))
            .andExpect(jsonPath("$.licenseId").value(DEFAULT_LICENSE_ID.intValue()))
            .andExpect(jsonPath("$.keyInstanceId").value(DEFAULT_KEY_INSTANCE_ID.intValue()))
            .andExpect(jsonPath("$.validity").value(DEFAULT_VALIDITY))
            .andExpect(jsonPath("$.dateBegin").value(DEFAULT_DATE_BEGIN.toString()))
            .andExpect(jsonPath("$.dateEnd").value(DEFAULT_DATE_END.toString()))
            .andExpect(jsonPath("$.extensionsNumber").value(DEFAULT_EXTENSIONS_NUMBER));
    }
    @Test
    @Transactional
    public void getNonExistingLicense() throws Exception {
        // Get the license
        restLicenseMockMvc.perform(get("/api/licenses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLicense() throws Exception {
        // Initialize the database
        licenseRepository.saveAndFlush(license);

        int databaseSizeBeforeUpdate = licenseRepository.findAll().size();

        // Update the license
        License updatedLicense = licenseRepository.findById(license.getId()).get();
        // Disconnect from session so that the updates on updatedLicense are not directly saved in db
        em.detach(updatedLicense);
        updatedLicense
            .licenseId(UPDATED_LICENSE_ID)
            .keyInstanceId(UPDATED_KEY_INSTANCE_ID)
            .validity(UPDATED_VALIDITY)
            .dateBegin(UPDATED_DATE_BEGIN)
            .dateEnd(UPDATED_DATE_END)
            .extensionsNumber(UPDATED_EXTENSIONS_NUMBER);
        LicenseDTO licenseDTO = licenseMapper.toDto(updatedLicense);

        restLicenseMockMvc.perform(put("/api/licenses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(licenseDTO)))
            .andExpect(status().isOk());

        // Validate the License in the database
        List<License> licenseList = licenseRepository.findAll();
        assertThat(licenseList).hasSize(databaseSizeBeforeUpdate);
        License testLicense = licenseList.get(licenseList.size() - 1);
        assertThat(testLicense.getLicenseId()).isEqualTo(UPDATED_LICENSE_ID);
        assertThat(testLicense.getKeyInstanceId()).isEqualTo(UPDATED_KEY_INSTANCE_ID);
        assertThat(testLicense.getValidity()).isEqualTo(UPDATED_VALIDITY);
        assertThat(testLicense.getDateBegin()).isEqualTo(UPDATED_DATE_BEGIN);
        assertThat(testLicense.getDateEnd()).isEqualTo(UPDATED_DATE_END);
        assertThat(testLicense.getExtensionsNumber()).isEqualTo(UPDATED_EXTENSIONS_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingLicense() throws Exception {
        int databaseSizeBeforeUpdate = licenseRepository.findAll().size();

        // Create the License
        LicenseDTO licenseDTO = licenseMapper.toDto(license);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLicenseMockMvc.perform(put("/api/licenses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(licenseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the License in the database
        List<License> licenseList = licenseRepository.findAll();
        assertThat(licenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLicense() throws Exception {
        // Initialize the database
        licenseRepository.saveAndFlush(license);

        int databaseSizeBeforeDelete = licenseRepository.findAll().size();

        // Delete the license
        restLicenseMockMvc.perform(delete("/api/licenses/{id}", license.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<License> licenseList = licenseRepository.findAll();
        assertThat(licenseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
