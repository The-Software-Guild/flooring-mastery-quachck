package com.wileyedge.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wileyedge.model.Area;
import com.wileyedge.model.Customer;
import com.wileyedge.model.Order;
import com.wileyedge.model.Product;
import com.wileyedge.model.State;

@Component
public class OrderView {

	private final UserIO userIO;

	@Autowired
	public OrderView(UserIO useruserIO) {
		this.userIO = useruserIO;
	}

	public void displayMenu() {
		userIO.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		userIO.print("* <<Flooring Program>>");
		userIO.print("* 1. Display Orders");
		userIO.print("* 2. Add an Order");
		userIO.print("* 3. Edit an Order");
		userIO.print("* 4. Remove an Order");
		userIO.print("* 5. Quit");
		userIO.print("*");
		userIO.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
	}

	public int readMenuSelection() {
		return userIO.readInt("Please select from the above options: ");
	}

	public void displayUnknownCommand() {
		userIO.print("Unknown command. Please enter a valid option.");
	}

	public void displayExitMessage() {
		userIO.print("Thank you for using Flooring Program. Goodbye!");
	}

	public void displayOrders(List<Order> orders) {
		for (Order order : orders) {
			userIO.print(order.toString());
		}
	}

	public Order getNewOrderData(List<Product> products, List<State> states) {
		Order order = new Order();
		order.setOrderDate(getOrderDate(false));
		Customer customer = new Customer();
		customer.setName(getCustomerName());
		order.setCustomer(customer);
		order.setState(getState(states));
		order.setProduct(getProductChoice(products));
		Area area = new Area();
		area.setSize(getAreaSize());
		order.setArea(area);

		return order;
	}

	public Order getUpdatedOrderData(Order existingOrder, List<Product> products, List<State> states) {
		Order updatedOrder = new Order();
		updatedOrder.setOrderDate(existingOrder.getOrderDate());
		updatedOrder.setCustomer(getUpdatedCustomerData(existingOrder.getCustomer()));
		updatedOrder.setState(getUpdatedState(existingOrder.getState(), states));
		updatedOrder.setProduct(getUpdatedProductData(existingOrder.getProduct(), products));
		updatedOrder.setArea(getUpdatedAreaSize(existingOrder.getArea()));
		return updatedOrder;
	}

	public LocalDate getOrderDate(boolean allowPastDate) {
		LocalDate orderDate;
		LocalDate currentDate = LocalDate.now();

		while (true) {
			orderDate = userIO.readLocalDate("Enter Order Date (MMDDYYYY): ");

			if (!allowPastDate && orderDate.isBefore(currentDate)) {
				userIO.print("Invalid date. Order date cannot be in the past. Please try again.");
			} else {
				break;
			}
		}

		return orderDate;
	}

	public String getCustomerName() {
		String name;
		while (true) {
			name = userIO.readString("Enter Customer Name: ");
			if (!name.matches("[a-zA-Z]+")) {
				userIO.print("Invalid name.");
			} else {
				break;
			}
		}
		return name;
	}

	public Customer getUpdatedCustomerData(Customer existingCustomer) {
		String customerName = userIO.readString("Enter customer name (" + existingCustomer.getName() + "): ");
		if (!customerName.isEmpty()) {
			existingCustomer.setName(customerName);
		}
		return existingCustomer;
	}

	public State getState(List<State> states) {
		State state = null;
		while (true) {
			userIO.print("Available States:");
			displayStateList(states);
			String stateChoice = userIO
					.readString("Please enter the number corresponding to the state (enter number): ");
			try {
				int choice = Integer.parseInt(stateChoice);
				if (choice >= 1 && choice <= states.size()) {
					state = states.get(choice - 1);
					break;
				} else {
					userIO.print("Invalid state choice. Please try again.");
				}
			} catch (NumberFormatException e) {
				userIO.print("Invalid input. Please enter a number corresponding to the state.");
			}
		}
		return state;
	}

	public State getUpdatedState(State existingState, List<State> states) {
		while (true) {
			userIO.print("Available States:");
			displayStateList(states);
			if (existingState != null) {
				userIO.print((states.size() + 1) + ". Keep current selection");
			}
			String stateChoice = userIO
					.readString("Please enter the number corresponding to the state (enter number): ");
			try {
				int choice = Integer.parseInt(stateChoice);
				if (choice >= 1 && choice <= states.size()) {
					return states.get(choice - 1);
				} else if (existingState != null && choice == states.size() + 1) {
					// User selected "Keep current selection"
					return existingState;
				} else {
					userIO.print("Invalid state choice. Please try again.");
				}
			} catch (NumberFormatException e) {
				userIO.print("Invalid input. Please enter a number corresponding to the state.");
			}
		}
	}

	public void displayStateList(List<State> states) {
		for (int i = 0; i < states.size(); i++) {
			userIO.print((i + 1) + ". " + states.get(i).getName());
		}
	}

	public Product getProductChoice(List<Product> products) {
		displayProductList(products);
		int productChoice;
		while (true) {
			productChoice = userIO.readInt("Please choose a product from the list above (enter number): ", 1,
					products.size());
			if (productChoice < 1 || productChoice > products.size()) {
				userIO.print("Invalid product choice. Please try again.");
			} else {
				break;
			}
		}
		return products.get(productChoice - 1);
	}

	private Product getUpdatedProductData(Product existingProduct, List<Product> allProducts) {
		displayProductListWithKeepCurrentSelection(allProducts);
		int productChoice;
		while (true) {
			productChoice = userIO.readInt("Please choose a product from the list above (enter number): ", 1,
					allProducts.size() + 1); // Include the "Keep current selection" option
			if (productChoice < 1 || productChoice > allProducts.size() + 1) {
				userIO.print("Invalid product choice. Please try again.");
			} else {
				break;
			}
		}
		if (productChoice == allProducts.size() + 1) {
			// User selected "Keep current selection"
			return existingProduct;
		} else {
			// User selected a specific product
			return allProducts.get(productChoice - 1);
		}
	}

	public void displayProductList(List<Product> products) {
		for (int i = 0; i < products.size(); i++) {
			Product product = products.get(i);
			userIO.print(
					(i + 1) + ". " + product.getType() + " - Cost Per Square Foot: $" + product.getCostPerSquareFoot()
							+ " - Labor Cost Per Square Foot: $" + product.getLaborCostPerSquareFoot());
		}
	}

	public void displayProductListWithKeepCurrentSelection(List<Product> products) {
		displayProductList(products);
		userIO.print((products.size() + 1) + ". Keep current selection");
	}

	public BigDecimal getAreaSize() {
		BigDecimal areaSize;
		while (true) {
			areaSize = userIO.readBigDecimal("Enter Area: ");
			if (areaSize.compareTo(BigDecimal.valueOf(100)) < 0) {
				userIO.print("Area must be greater than or equal to 100. Please try again.");
			} else {
				break;
			}
		}
		return areaSize;
	}

	private Area getUpdatedAreaSize(Area existingArea) {
		String areaInput = userIO.readString("Enter area (" + existingArea.getSize() + "): ");
		if (!areaInput.isEmpty()) {
			try {
				BigDecimal areaSize = new BigDecimal(areaInput);
				if (areaSize.compareTo(BigDecimal.ZERO) > 0) {
					existingArea.setSize(areaSize);
				}
			} catch (NumberFormatException e) {
				userIO.print("Invalid input. Please enter a decimal value.");
			}
		}
		return existingArea;
	}

	public int getOrderNumber() {
		return userIO.readInt("Enter Order Number: ");
	}

	public boolean confirm(String message) {
		String response = userIO.readString(message + " (Y/N) ");
		return response.equalsIgnoreCase("y");
	}

	public void displayErrorMessage(String errorMsg) {
		userIO.print("ERROR: " + errorMsg);
	}

	public void displaySuccessMessage(String message) {
		userIO.print(message);
	}

	public void displayOrderSummary(Order order) {
		userIO.print("Order Summary:");
		userIO.print("---------------");
		userIO.print("Order Date: " + order.getOrderDate());
		userIO.print("Customer: " + order.getCustomer().getName());
		userIO.print("State: " + order.getState().getAbbrv());
		userIO.print("Product: " + order.getProduct().getType());
		userIO.print("Area Size: " + order.getArea().getSize() + "sqr.ft");
		userIO.print("Material Cost: $" + order.getMaterialCost());
		userIO.print("Labor Cost: $" + order.getLaborCost());
		userIO.print("Tax: $" + order.getTax());
		userIO.print("Total Cost: $" + order.getTotal());
	}

	public void displayMessage(String message) {
		userIO.print(message);
	}
}
