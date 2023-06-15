package com.wileyedge.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

public class Order {

	private int orderId;

	private LocalDate orderDate;

	private Customer customer;

	private State state;

	private Product product;

	private Area area;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public BigDecimal getMaterialCost() {
		return product.getCostPerSquareFoot().multiply(area.getSize());
	}

	public BigDecimal getLaborCost() {
		return product.getLaborCostPerSquareFoot().multiply(area.getSize());
	}

	public BigDecimal getTax() {
	    BigDecimal tax = (getMaterialCost().add(getLaborCost())).multiply(state.getTaxRate().divide(new BigDecimal("100")));
	    return tax.setScale(2, RoundingMode.HALF_UP);
	}


	@Override
	public String toString() {
		return orderId + "," + customer.getName() + "," + state.getAbbrv() + "," + state.getTaxRate() + ","
				+ product.getType() + "," + area.getSize() + "," + product.getCostPerSquareFoot() + ","
				+ product.getLaborCostPerSquareFoot() + "," + getMaterialCost() + "," + getLaborCost() + "," + getTax()
				+ "," + getTotal();
	}

	public BigDecimal getTotal() {
	    BigDecimal total = getMaterialCost().add(getLaborCost()).add(getTax());
	    return total.setScale(2, RoundingMode.HALF_UP);
	}


	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Order order = (Order) o;
		return orderId == order.orderId && Objects.equals(orderDate, order.orderDate)
				&& Objects.equals(customer, order.customer) && Objects.equals(state, order.state)
				&& Objects.equals(product, order.product) && Objects.equals(area, order.area);
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId, orderDate, customer, state, product, area);
	}

}
