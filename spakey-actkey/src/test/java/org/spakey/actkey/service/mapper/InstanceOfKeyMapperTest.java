package org.spakey.actkey.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spakey.actkey.service.mapper.impl.InstanceOfKeyMapperImpl;

import static org.assertj.core.api.Assertions.assertThat;

public class InstanceOfKeyMapperTest {

    private InstanceOfKeyMapper instanceOfKeyMapper;

    @BeforeEach
    public void setUp() {
        instanceOfKeyMapper = new InstanceOfKeyMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(instanceOfKeyMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(instanceOfKeyMapper.fromId(null)).isNull();
    }
}
