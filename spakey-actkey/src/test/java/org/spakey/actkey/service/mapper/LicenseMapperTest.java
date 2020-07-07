package org.spakey.actkey.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spakey.actkey.service.mapper.impl.LicenseMapperImpl;

import static org.assertj.core.api.Assertions.assertThat;

public class LicenseMapperTest {

    private LicenseMapper licenseMapper;

    @BeforeEach
    public void setUp() {
        licenseMapper = new LicenseMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(licenseMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(licenseMapper.fromId(null)).isNull();
    }
}
