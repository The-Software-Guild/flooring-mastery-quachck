package com.wileyedge.model;

import java.math.BigDecimal;
import java.util.Objects;

public class State {

	private String name;

	private String abbrv;

	private BigDecimal taxRate;

	public String getAbbrv() {
		return abbrv;
	}

	public void setAbbrv(String abbrv) {
		this.abbrv = abbrv;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		State state = (State) o;
		return Objects.equals(name, state.name) && Objects.equals(abbrv, state.abbrv)
				&& (taxRate != null && taxRate.compareTo(state.taxRate) == 0);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, abbrv, taxRate);
	}

}
