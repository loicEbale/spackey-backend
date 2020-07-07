package org.spakey.actkey.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.spakey.actkey.web.rest.TestUtil;

public class LicenseDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LicenseDTO.class);
        LicenseDTO licenseDTO1 = new LicenseDTO();
        licenseDTO1.setId(1L);
        LicenseDTO licenseDTO2 = new LicenseDTO();
        assertThat(licenseDTO1).isNotEqualTo(licenseDTO2);
        licenseDTO2.setId(licenseDTO1.getId());
        assertThat(licenseDTO1).isEqualTo(licenseDTO2);
        licenseDTO2.setId(2L);
        assertThat(licenseDTO1).isNotEqualTo(licenseDTO2);
        licenseDTO1.setId(null);
        assertThat(licenseDTO1).isNotEqualTo(licenseDTO2);
    }
}
