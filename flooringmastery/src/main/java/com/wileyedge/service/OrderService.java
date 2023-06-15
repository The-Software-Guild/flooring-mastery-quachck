package com.wileyedge.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wileyedge.exception.InvalidOrderException;
import com.wileyedge.model.Order;
import com.wileyedge.model.Product;
import com.wileyedge.model.State;

@Service
public interface OrderService {
	Order addOrder(Order order) throws InvalidOrderException;
	
	Order retrieveOrder(int orderNumber, LocalDate date);

	List<Order> getOrdersByDate(LocalDate date);

	Order editOrder(int orderNumber, Order order);

	List<Order> retrieveOrders(LocalDate date);
	
	Order removeOrder(int orderNumber, LocalDate orderDate);

	boolean doesStateExist(String state);

	boolean doesProductExist(String productType);

	List<Product> getAllProducts();
	
	List<State> retrieveAllStates();
}
