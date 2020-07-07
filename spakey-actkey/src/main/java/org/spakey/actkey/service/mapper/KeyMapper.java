package org.spakey.actkey.service.mapper;


import org.spakey.actkey.domain.*;
import org.spakey.actkey.service.dto.KeyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Key} and its DTO {@link KeyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface KeyMapper extends EntityMapper<KeyDTO, Key> {


    @Mapping(target = "instanceOfKeys", ignore = true)
    @Mapping(target = "removeInstanceOfKey", ignore = true)
    Key toEntity(KeyDTO keyDTO);

    default Key fromId(Long id) {
        if (id == null) {
            return null;
        }
        Key key = new Key();
        key.setId(id);
        return key;
    }
}
