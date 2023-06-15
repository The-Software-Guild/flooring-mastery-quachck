package com.wileyedge.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wileyedge.model.Order;

@Repository
public interface OrderDao {

	Order addOrder(Order order);

	Order editOrder(int orderNumber, Order order);

	Order removeOrder(int orderNumber, LocalDate orderDate);

	List<Order> retrieveOrders(LocalDate date);

	Order retrieveOrder(int orderNumber, LocalDate orderDate);
}
