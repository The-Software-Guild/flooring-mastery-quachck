package com.wileyedge.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

	private String type;

	private BigDecimal costPerSquareFoot;

	private BigDecimal laborCostPerSquareFoot;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getCostPerSquareFoot() {
		return costPerSquareFoot;
	}

	public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
		this.costPerSquareFoot = costPerSquareFoot;
	}

	public BigDecimal getLaborCostPerSquareFoot() {
		return laborCostPerSquareFoot;
	}

	public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
		this.laborCostPerSquareFoot = laborCostPerSquareFoot;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Product product = (Product) o;
	    return Objects.equals(type, product.type) &&
	           (costPerSquareFoot != null && costPerSquareFoot.compareTo(product.costPerSquareFoot) == 0) &&
	           (laborCostPerSquareFoot != null && laborCostPerSquareFoot.compareTo(product.laborCostPerSquareFoot) == 0);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(type, costPerSquareFoot, laborCostPerSquareFoot);
	}


}
