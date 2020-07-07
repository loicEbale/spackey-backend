package org.spakey.product.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "product_id", nullable = false, unique = true)
    private Long product_id;

    @Column(name = "product_name")
    private String product_name;

    @Column(name = "product_ref")
    private String product_ref;

    @Column(name = "product_version")
    private String product_version;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public Product product_id(Long product_id) {
        this.product_id = product_id;
        return this;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public Product product_name(String product_name) {
        this.product_name = product_name;
        return this;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_ref() {
        return product_ref;
    }

    public Product product_ref(String product_ref) {
        this.product_ref = product_ref;
        return this;
    }

    public void setProduct_ref(String product_ref) {
        this.product_ref = product_ref;
    }

    public String getProduct_version() {
        return product_version;
    }

    public Product product_version(String product_version) {
        this.product_version = product_version;
        return this;
    }

    public void setProduct_version(String product_version) {
        this.product_version = product_version;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", product_id=" + getProduct_id() +
            ", product_name='" + getProduct_name() + "'" +
            ", product_ref='" + getProduct_ref() + "'" +
            ", product_version='" + getProduct_version() + "'" +
            "}";
    }
}
