package com.wileyedge.controller;

import com.wileyedge.exception.InvalidOrderException;
import com.wileyedge.model.Order;
import com.wileyedge.model.Product;
import com.wileyedge.model.State;
import com.wileyedge.service.OrderService;
import com.wileyedge.view.OrderView;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {

	private final OrderService orderService;
	private final OrderView orderView;

	@Autowired
	public OrderController(OrderService orderService, OrderView orderView) {
		this.orderService = orderService;
		this.orderView = orderView;
	}

	public void run() {
		boolean keepGoing = true;

		while (keepGoing) {
			orderView.displayMenu();
			int menuSelection = orderView.readMenuSelection();
			switch (menuSelection) {
			case 1:
				displayOrders();
				break;
			case 2:
				addOrder();
				break;
			case 3:
				editOrder();
				break;
			case 4:
				removeOrder();
				break;
			case 5:
				keepGoing = false;
				exitMessage();
				break;
			default:
				unknownCommand();
			}
		}
		exitMessage();
	}

	private void displayOrders() {
		LocalDate date = orderView.getOrderDate(true);
		List<Order> orders = orderService.retrieveOrders(date);
		
		if (orders.isEmpty()) {
			orderView.displayErrorMessage("No orders found for the specified date.");
		} else {
			orderView.displayOrders(orders);
		}
	}
	
	private void addOrder() {
		List<Product> allProducts = orderService.getAllProducts();
		List<State> allStates = orderService.retrieveAllStates();
		Order newOrder = orderView.getNewOrderData(allProducts, allStates);

		orderView.displayOrderSummary(newOrder); // Display the order summary

		if (orderView.confirm("Confirm the order")) {
			try {
				orderService.addOrder(newOrder);
				orderView.displaySuccessMessage("Order successfully added.");
			} catch (InvalidOrderException e) {
				e.printStackTrace();
				orderView.displayErrorMessage("Error occurred while adding the order.");
			}
		} else {
			orderView.displayMessage("Order cancelled.");
		}
	}

	public void editOrder() {
		List<Product> allProducts = orderService.getAllProducts();
		List<State> allStates = orderService.retrieveAllStates();
		LocalDate orderDate = orderView.getOrderDate(false);
		int orderNumber = orderView.getOrderNumber();
		Order existingOrder = orderService.retrieveOrder(orderNumber, orderDate);

		if (existingOrder == null) {
			orderView.displayErrorMessage("Order not found for the specified order number and date.");
			return;
		}

		Order updatedOrder = orderView.getUpdatedOrderData(existingOrder, allProducts, allStates);
		updatedOrder.setOrderId(orderNumber);
		updatedOrder.setOrderDate(orderDate);
		orderView.displayOrderSummary(updatedOrder);

		boolean confirm = orderView.confirm("Do you want to save the changes to this order?");
		if (confirm) {
			orderService.editOrder(orderNumber, updatedOrder);
			orderView.displaySuccessMessage("Order successfully updated.");
		} else {
			orderView.displayMessage("Edit canceled. The original order remains unchanged.");
		}
	}

	private void removeOrder() {
		LocalDate date = orderView.getOrderDate(true);
		int orderNumber = orderView.getOrderNumber();

		Order orderToRemove = orderService.retrieveOrder(orderNumber, date);

		if (orderToRemove != null) {
			orderToRemove.setOrderDate(date);
			orderView.displayOrderSummary(orderToRemove);
			if (orderView.confirm("Are you sure you want to remove this order?")) {
				orderService.removeOrder(orderNumber, date);
				orderView.displaySuccessMessage("Order successfully removed.");
			} else {
				orderView.displayMessage("Order removal cancelled.");
			}
		} else {
			orderView.displayErrorMessage("Order not found for the specified date and order number.");
		}
	}

	private void exitMessage() {
		orderView.displayMessage("Exiting the application. Goodbye!");
	}

	private void unknownCommand() {
		orderView.displayErrorMessage("Unknown command. Please try again.");
	}
}
