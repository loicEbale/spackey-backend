package org.spakey.actkey.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.spakey.actkey.web.rest.TestUtil;

public class InstanceOfKeyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstanceOfKeyDTO.class);
        InstanceOfKeyDTO instanceOfKeyDTO1 = new InstanceOfKeyDTO();
        instanceOfKeyDTO1.setId(1L);
        InstanceOfKeyDTO instanceOfKeyDTO2 = new InstanceOfKeyDTO();
        assertThat(instanceOfKeyDTO1).isNotEqualTo(instanceOfKeyDTO2);
        instanceOfKeyDTO2.setId(instanceOfKeyDTO1.getId());
        assertThat(instanceOfKeyDTO1).isEqualTo(instanceOfKeyDTO2);
        instanceOfKeyDTO2.setId(2L);
        assertThat(instanceOfKeyDTO1).isNotEqualTo(instanceOfKeyDTO2);
        instanceOfKeyDTO1.setId(null);
        assertThat(instanceOfKeyDTO1).isNotEqualTo(instanceOfKeyDTO2);
    }
}
