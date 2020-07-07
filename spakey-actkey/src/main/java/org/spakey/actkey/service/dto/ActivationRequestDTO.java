package org.spakey.actkey.service.dto;

import java.io.Serializable;

public class ActivationRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long userId;
	
	private Long productId;
	
	private Integer activationNumber;
	
	private int validity;
	
	

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

	public int getValidity() {
		return validity;
	}

	public void setValidity(int validity) {
		this.validity = validity;
	}
	
	
	
}
