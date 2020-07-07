package org.spakey.actkey.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A InstanceOfKey.
 */
@Entity
@Table(name = "instance_of_key")
public class InstanceOfKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "key_instance_id", unique = true)
    private Long keyInstanceId;

    @Column(name = "activation_key")
    private String activationKey;

    @Column(name = "license_id")
    private Long licenseId;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "activation_date")
    private LocalDate activationDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "activationKey", allowSetters = true)
    private Key key;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKeyInstanceId() {
        return keyInstanceId;
    }

    public InstanceOfKey keyInstanceId(Long keyInstanceId) {
        this.keyInstanceId = keyInstanceId;
        return this;
    }

    public void setKeyInstanceId(Long keyInstanceId) {
        this.keyInstanceId = keyInstanceId;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public InstanceOfKey activationKey(String activationKey) {
        this.activationKey = activationKey;
        return this;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public Long getLicenseId() {
        return licenseId;
    }

    public InstanceOfKey licenseId(Long licenseId) {
        this.licenseId = licenseId;
        return this;
    }

    public void setLicenseId(Long licenseId) {
        this.licenseId = licenseId;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public InstanceOfKey isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDate getActivationDate() {
        return activationDate;
    }

    public InstanceOfKey activationDate(LocalDate activationDate) {
        this.activationDate = activationDate;
        return this;
    }

    public void setActivationDate(LocalDate activationDate) {
        this.activationDate = activationDate;
    }

    public Key getKey() {
        return key;
    }

    public InstanceOfKey key(Key key) {
        this.key = key;
        return this;
    }

    public void setKey(Key key) {
        this.key = key;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InstanceOfKey)) {
            return false;
        }
        return id != null && id.equals(((InstanceOfKey) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InstanceOfKey{" +
            "id=" + getId() +
            ", keyInstanceId=" + getKeyInstanceId() +
            ", activationKey='" + getActivationKey() + "'" +
            ", licenseId=" + getLicenseId() +
            ", isActive='" + isIsActive() + "'" +
            ", activationDate='" + getActivationDate() + "'" +
            "}";
    }
}
