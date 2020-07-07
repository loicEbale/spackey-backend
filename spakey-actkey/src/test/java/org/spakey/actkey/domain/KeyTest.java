package org.spakey.actkey.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.spakey.actkey.web.rest.TestUtil;

public class KeyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Key.class);
        Key key1 = new Key();
        key1.setId(1L);
        Key key2 = new Key();
        key2.setId(key1.getId());
        assertThat(key1).isEqualTo(key2);
        key2.setId(2L);
        assertThat(key1).isNotEqualTo(key2);
        key1.setId(null);
        assertThat(key1).isNotEqualTo(key2);
    }
}
