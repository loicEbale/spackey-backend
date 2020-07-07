package org.spakey.actkey.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Key.
 */
@Entity
@Table(name = "jhi_key")
public class Key implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "activation_key", nullable = false, unique = true)
    private String activationKey;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "activation_number")
    private Integer activationNumber;

    @OneToMany(mappedBy = "key")
    private Set<InstanceOfKey> instanceOfKeys = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public Key activationKey(String activationKey) {
        this.activationKey = activationKey;
        return this;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public Long getUserId() {
        return userId;
    }

    public Key userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public Key productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getActivationNumber() {
        return activationNumber;
    }

    public Key activationNumber(Integer activationNumber) {
        this.activationNumber = activationNumber;
        return this;
    }

    public void setActivationNumber(Integer activationNumber) {
        this.activationNumber = activationNumber;
    }

    public Set<InstanceOfKey> getInstanceOfKeys() {
        return instanceOfKeys;
    }

    public Key instanceOfKeys(Set<InstanceOfKey> instanceOfKeys) {
        this.instanceOfKeys = instanceOfKeys;
        return this;
    }

    public Key addInstanceOfKey(InstanceOfKey instanceOfKey) {
        this.instanceOfKeys.add(instanceOfKey);
        instanceOfKey.setKey(this);
        return this;
    }

    public Key removeInstanceOfKey(InstanceOfKey instanceOfKey) {
        this.instanceOfKeys.remove(instanceOfKey);
        instanceOfKey.setKey(null);
        return this;
    }

    public void setInstanceOfKeys(Set<InstanceOfKey> instanceOfKeys) {
        this.instanceOfKeys = instanceOfKeys;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Key)) {
            return false;
        }
        return id != null && id.equals(((Key) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Key{" +
            "id=" + getId() +
            ", activationKey='" + getActivationKey() + "'" +
            ", userId=" + getUserId() +
            ", productId=" + getProductId() +
            ", activationNumber=" + getActivationNumber() +
            "}";
    }
}
