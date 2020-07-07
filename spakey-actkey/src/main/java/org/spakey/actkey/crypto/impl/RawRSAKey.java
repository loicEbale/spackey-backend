package org.spakey.actkey.crypto.impl;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.Getter;

@Getter
public class RawRSAKey implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigInteger modulus;
	private BigInteger exponent;

	public RawRSAKey(BigInteger modulus, BigInteger exponent) {
		this.modulus = modulus;
		this.exponent = exponent;
	}
}
