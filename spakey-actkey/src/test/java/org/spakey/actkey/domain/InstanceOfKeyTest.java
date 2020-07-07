package org.spakey.actkey.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.spakey.actkey.web.rest.TestUtil;

public class InstanceOfKeyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstanceOfKey.class);
        InstanceOfKey instanceOfKey1 = new InstanceOfKey();
        instanceOfKey1.setId(1L);
        InstanceOfKey instanceOfKey2 = new InstanceOfKey();
        instanceOfKey2.setId(instanceOfKey1.getId());
        assertThat(instanceOfKey1).isEqualTo(instanceOfKey2);
        instanceOfKey2.setId(2L);
        assertThat(instanceOfKey1).isNotEqualTo(instanceOfKey2);
        instanceOfKey1.setId(null);
        assertThat(instanceOfKey1).isNotEqualTo(instanceOfKey2);
    }
}
