package org.spakey.actkey.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.spakey.actkey.web.rest.TestUtil;

public class KeyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KeyDTO.class);
        KeyDTO keyDTO1 = new KeyDTO();
        keyDTO1.setId(1L);
        KeyDTO keyDTO2 = new KeyDTO();
        assertThat(keyDTO1).isNotEqualTo(keyDTO2);
        keyDTO2.setId(keyDTO1.getId());
        assertThat(keyDTO1).isEqualTo(keyDTO2);
        keyDTO2.setId(2L);
        assertThat(keyDTO1).isNotEqualTo(keyDTO2);
        keyDTO1.setId(null);
        assertThat(keyDTO1).isNotEqualTo(keyDTO2);
    }
}
