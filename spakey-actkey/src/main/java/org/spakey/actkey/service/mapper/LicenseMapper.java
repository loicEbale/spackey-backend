package org.spakey.actkey.service.mapper;


import org.spakey.actkey.domain.*;
import org.spakey.actkey.service.dto.LicenseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link License} and its DTO {@link LicenseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LicenseMapper extends EntityMapper<LicenseDTO, License> {


    @Mapping(target = "has", ignore = true)
    License toEntity(LicenseDTO licenseDTO);

    default License fromId(Long id) {
        if (id == null) {
            return null;
        }
        License license = new License();
        license.setId(id);
        return license;
    }
}
