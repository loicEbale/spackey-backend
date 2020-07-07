package org.spakey.actkey.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.spakey.actkey.domain.Key} entity.
 */
public class KeyDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    @NotNull
    private String activationKey;

    private Long userId;

    private Long productId;

    private Integer activationNumber;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getActivationNumber() {
        return activationNumber;
    }

    public void setActivationNumber(Integer activationNumber) {
        this.activationNumber = activationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KeyDTO)) {
            return false;
        }

        return id != null && id.equals(((KeyDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KeyDTO{" +
            "id=" + getId() +
            ", activationKey='" + getActivationKey() + "'" +
            ", userId=" + getUserId() +
            ", productId=" + getProductId() +
            ", activationNumber=" + getActivationNumber() +
            "}";
    }
}
