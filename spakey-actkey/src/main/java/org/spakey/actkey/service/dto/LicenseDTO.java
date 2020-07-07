package org.spakey.actkey.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.spakey.actkey.domain.License} entity.
 */
public class LicenseDTO implements Serializable {
    
    private Long id;

    
    private Long licenseId;

    private Long keyInstanceId;

    private Integer validity;

    private LocalDate dateBegin;

    private LocalDate dateEnd;

    private Integer extensionsNumber;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(Long licenseId) {
        this.licenseId = licenseId;
    }

    public Long getKeyInstanceId() {
        return keyInstanceId;
    }

    public void setKeyInstanceId(Long keyInstanceId) {
        this.keyInstanceId = keyInstanceId;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public LocalDate getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(LocalDate dateBegin) {
        this.dateBegin = dateBegin;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getExtensionsNumber() {
        return extensionsNumber;
    }

    public void setExtensionsNumber(Integer extensionsNumber) {
        this.extensionsNumber = extensionsNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LicenseDTO)) {
            return false;
        }

        return id != null && id.equals(((LicenseDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LicenseDTO{" +
            "id=" + getId() +
            ", licenseId=" + getLicenseId() +
            ", keyInstanceId=" + getKeyInstanceId() +
            ", validity=" + getValidity() +
            ", dateBegin='" + getDateBegin() + "'" +
            ", dateEnd='" + getDateEnd() + "'" +
            ", extensionsNumber=" + getExtensionsNumber() +
            "}";
    }
}
