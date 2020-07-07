package org.spakey.actkey.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spakey.actkey.service.mapper.impl.KeyMapperImpl;

import static org.assertj.core.api.Assertions.assertThat;

public class KeyMapperTest {

    private KeyMapper keyMapper;

    @BeforeEach
    public void setUp() {
        keyMapper = new KeyMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(keyMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(keyMapper.fromId(null)).isNull();
    }
}
