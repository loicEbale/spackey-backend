package org.spakey.product.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.spakey.product.domain.Product} entity.
 */
public class ProductDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Long product_id;

    private String product_name;

    private String product_ref;

    private String product_version;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_ref() {
        return product_ref;
    }

    public void setProduct_ref(String product_ref) {
        this.product_ref = product_ref;
    }

    public String getProduct_version() {
        return product_version;
    }

    public void setProduct_version(String product_version) {
        this.product_version = product_version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", product_id=" + getProduct_id() +
            ", product_name='" + getProduct_name() + "'" +
            ", product_ref='" + getProduct_ref() + "'" +
            ", product_version='" + getProduct_version() + "'" +
            "}";
    }
}
