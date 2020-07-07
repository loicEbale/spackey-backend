package org.spakey.actkey.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link org.spakey.actkey.domain.InstanceOfKey} entity.
 */
public class InstanceOfKeyDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Long id;

    
    private Long keyInstanceId;

    private String activationKey;

    private Long licenseId;

    private Boolean isActive;

    private LocalDate activationDate;


    private Long keyId;

    private String keyActivationKey;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKeyInstanceId() {
        return keyInstanceId;
    }

    public void setKeyInstanceId(Long keyInstanceId) {
        this.keyInstanceId = keyInstanceId;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public Long getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(Long licenseId) {
        this.licenseId = licenseId;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDate getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(LocalDate activationDate) {
        this.activationDate = activationDate;
    }

    public Long getKeyId() {
        return keyId;
    }

    public void setKeyId(Long keyId) {
        this.keyId = keyId;
    }

    public String getKeyActivationKey() {
        return keyActivationKey;
    }

    public void setKeyActivationKey(String keyActivationKey) {
        this.keyActivationKey = keyActivationKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InstanceOfKeyDTO)) {
            return false;
        }

        return id != null && id.equals(((InstanceOfKeyDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InstanceOfKeyDTO{" +
            "id=" + getId() +
            ", keyInstanceId=" + getKeyInstanceId() +
            ", activationKey='" + getActivationKey() + "'" +
            ", licenseId=" + getLicenseId() +
            ", isActive='" + isIsActive() + "'" +
            ", activationDate='" + getActivationDate() + "'" +
            ", keyId=" + getKeyId() +
            ", keyActivationKey='" + getKeyActivationKey() + "'" +
            "}";
    }
}
