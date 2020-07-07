package org.spakey.actkey.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A License.
 */
@Entity
@Table(name = "license")
public class License implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "license_id", unique = true)
    private Long licenseId;

    @Column(name = "key_instance_id")
    private Long keyInstanceId;

    @Column(name = "validity")
    private Integer validity;

    @Column(name = "date_begin")
    private LocalDate dateBegin;

    @Column(name = "date_end")
    private LocalDate dateEnd;

    @Column(name = "extensions_number")
    private Integer extensionsNumber;

    @OneToOne(mappedBy = "license")
    @JsonIgnore
    private InstanceOfKey has;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLicenseId() {
        return licenseId;
    }

    public License licenseId(Long licenseId) {
        this.licenseId = licenseId;
        return this;
    }

    public void setLicenseId(Long licenseId) {
        this.licenseId = licenseId;
    }

    public Long getKeyInstanceId() {
        return keyInstanceId;
    }

    public License keyInstanceId(Long keyInstanceId) {
        this.keyInstanceId = keyInstanceId;
        return this;
    }

    public void setKeyInstanceId(Long keyInstanceId) {
        this.keyInstanceId = keyInstanceId;
    }

    public Integer getValidity() {
        return validity;
    }

    public License validity(Integer validity) {
        this.validity = validity;
        return this;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public LocalDate getDateBegin() {
        return dateBegin;
    }

    public License dateBegin(LocalDate dateBegin) {
        this.dateBegin = dateBegin;
        return this;
    }

    public void setDateBegin(LocalDate dateBegin) {
        this.dateBegin = dateBegin;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public License dateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
        return this;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getExtensionsNumber() {
        return extensionsNumber;
    }

    public License extensionsNumber(Integer extensionsNumber) {
        this.extensionsNumber = extensionsNumber;
        return this;
    }

    public void setExtensionsNumber(Integer extensionsNumber) {
        this.extensionsNumber = extensionsNumber;
    }

    public InstanceOfKey getHas() {
        return has;
    }

    public License has(InstanceOfKey instanceOfKey) {
        this.has = instanceOfKey;
        return this;
    }

    public void setHas(InstanceOfKey instanceOfKey) {
        this.has = instanceOfKey;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof License)) {
            return false;
        }
        return id != null && id.equals(((License) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "License{" +
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
