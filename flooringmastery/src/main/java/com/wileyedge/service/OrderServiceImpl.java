package com.wileyedge.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wileyedge.dao.OrderDao;
import com.wileyedge.dao.ProductDao;
import com.wileyedge.dao.StateDao;
import com.wileyedge.exception.InvalidOrderException;
import com.wileyedge.model.Order;
import com.wileyedge.model.Product;
import com.wileyedge.model.State;

@Service
public class OrderServiceImpl implements OrderService {

	private OrderDao orderDao;
	private StateDao stateDao;
	private ProductDao productDao;

	@Autowired
	public OrderServiceImpl(OrderDao orderDao, StateDao stateDao, ProductDao productDao) {
		this.orderDao = orderDao;
		this.stateDao = stateDao;
		this.productDao = productDao;
	}
	
	@Override
	public Order addOrder(Order order) throws InvalidOrderException {
//		validateOrder(order);
		return orderDao.addOrder(order);
	}
	
	@Override
	public List<Order> getOrdersByDate(LocalDate date) {
		return orderDao.retrieveOrders(date);
	}

	@Override
	public Order editOrder(int orderNumber, Order order) {
		return orderDao.editOrder(orderNumber, order);
	}

	@Override
	public List<Order> retrieveOrders(LocalDate date) {
		return orderDao.retrieveOrders(date);
	}

	@Override
	public boolean doesStateExist(String state) {
		List<State> states = retrieveAllStates();
		for (State s : states) {
			if (s.getName().equalsIgnoreCase(state)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<State> retrieveAllStates() {
		return stateDao.getAllStates();
	}
	
	@Override
	public List<Product> getAllProducts() {
	    return productDao.getAllProducts();
	}
	
	@Override
	public boolean doesProductExist(String productType) {
	    List<Product> products = getAllProducts();
	    return products.stream().anyMatch(p -> p.getType().equalsIgnoreCase(productType));
	}
	
	
	public void validateOrder(Order order) throws InvalidOrderException {
	    if (!doesStateExist(order.getState().getName())) {
	        throw new InvalidOrderException("The state you entered doesn't exist. Please enter a valid state.");
	    }
	    if (!doesProductExist(order.getProduct().getType())) {
	        throw new InvalidOrderException("The product type you entered doesn't exist. Please enter a valid product type.");
	    }
	}

	@Override
	public Order retrieveOrder(int orderNumber, LocalDate orderDate) {
	    return orderDao.retrieveOrder(orderNumber, orderDate);
	}

	@Override
	public Order removeOrder(int orderNumber, LocalDate orderDate) {
		return orderDao.removeOrder(orderNumber, orderDate);
	}

}
