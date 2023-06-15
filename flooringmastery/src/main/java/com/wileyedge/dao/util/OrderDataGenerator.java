package com.wileyedge.dao.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.wileyedge.model.Area;
import com.wileyedge.model.Customer;
import com.wileyedge.model.Order;
import com.wileyedge.model.Product;
import com.wileyedge.model.State;

public class OrderDataGenerator {

	// Generate the file name based on the order date
	public static String generateFileName(LocalDate orderDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
		String formattedDate = orderDate.format(formatter);
		return "Orders_" + formattedDate + ".txt";
	}

	// Generate the order data in the desired format
	public static String generateOrderData(Order order) {
		StringBuilder sb = new StringBuilder();

		sb.append(order.getCustomer().getName()).append(",");
		sb.append(order.getState().getAbbrv()).append(",");
		sb.append(order.getState().getTaxRate()).append(",");
		sb.append(order.getProduct().getType()).append(",");
		sb.append(order.getArea().getSize()).append(",");
		sb.append(order.getProduct().getCostPerSquareFoot()).append(",");
		sb.append(order.getProduct().getLaborCostPerSquareFoot()).append(",");
		sb.append(order.getMaterialCost()).append(",");
		sb.append(order.getLaborCost()).append(",");
		sb.append(order.getTax()).append(",");
		sb.append(order.getTotal());

		return sb.toString();
	}

	// Create an Order object from a line of the data file
	public static Order parseOrderData(String[] fields) {
		Order order = new Order();

		order.setOrderId(Integer.parseInt(fields[0]));

		Customer customer = new Customer();
		customer.setName(fields[1]);
		order.setCustomer(customer);

		State state = new State();
		state.setAbbrv(fields[2]);
		state.setTaxRate(new BigDecimal(fields[3]));
		order.setState(state);

		Product product = new Product();
		product.setType(fields[4]);
		product.setCostPerSquareFoot(new BigDecimal(fields[6]));
		product.setLaborCostPerSquareFoot(new BigDecimal(fields[7]));
		order.setProduct(product);

		Area area = new Area();
		area.setSize(new BigDecimal(fields[5]));
		order.setArea(area);

		return order;
	}
}
