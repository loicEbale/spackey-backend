package org.spakey.actkey.service.mapper;


import org.spakey.actkey.domain.*;
import org.spakey.actkey.service.dto.InstanceOfKeyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InstanceOfKey} and its DTO {@link InstanceOfKeyDTO}.
 */
@Mapper(componentModel = "spring", uses = {KeyMapper.class})
public interface InstanceOfKeyMapper extends EntityMapper<InstanceOfKeyDTO, InstanceOfKey> {

    @Mapping(source = "key.id", target = "keyId")
    @Mapping(source = "key.activationKey", target = "keyActivationKey")
    InstanceOfKeyDTO toDto(InstanceOfKey instanceOfKey);

    @Mapping(source = "keyId", target = "key")
    InstanceOfKey toEntity(InstanceOfKeyDTO instanceOfKeyDTO);

    default InstanceOfKey fromId(Long id) {
        if (id == null) {
            return null;
        }
        InstanceOfKey instanceOfKey = new InstanceOfKey();
        instanceOfKey.setId(id);
        return instanceOfKey;
    }
}
