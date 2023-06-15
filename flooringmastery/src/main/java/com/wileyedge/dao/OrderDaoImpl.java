package com.wileyedge.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.wileyedge.dao.util.OrderDataGenerator;

import com.wileyedge.model.Order;

@Repository
public class OrderDaoImpl implements OrderDao {
	
	@Value("${orders.folder.path}")
	private String ordersFolderPath;

	public Order addOrder(Order order) {
		if (order == null) {
			throw new IllegalArgumentException("Order cannot be null.");
		}
		
		if (ordersFolderPath == null) {
			throw new IllegalStateException("ordersFolderPath is not initialized.");
		}

		try {
			String fileName = OrderDataGenerator.generateFileName(order.getOrderDate());
			String orderData = OrderDataGenerator.generateOrderData(order);

			// Path to the order file
			Path orderFilePath = Paths.get(ordersFolderPath, fileName);

			// Check if the file exists
			if (!Files.exists(orderFilePath)) {
				// Create the file if it doesn't exist
				Files.createFile(orderFilePath);
			}

			// Read all lines of the file
			List<String> lines = Files.readAllLines(orderFilePath);

			// Get the latest order number from the file
			int latestOrderNumber = 0;
			if (!lines.isEmpty()) {
				String lastLine = lines.get(lines.size() - 1);
				String[] fields = lastLine.split(",");
				latestOrderNumber = Integer.parseInt(fields[0]);
			}

			// Generate the next order ID
			int nextOrderId = latestOrderNumber + 1;

			// Append the order data with the next order ID
			String nextOrderData = nextOrderId + "," + orderData;

			// Write the order data to the file (append to existing file)
			Files.write(orderFilePath, (nextOrderData + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);

			// Set the generated ID to the order
			order.setOrderId(nextOrderId);

			return order;
		} catch (IOException e) {
			throw new RuntimeException("Error processing order file", e);
		}
	}

	public Order editOrder(int orderId, Order newOrder) {
		if (newOrder == null) {
			throw new IllegalArgumentException("Order cannot be null.");
		}

		try {
			// Read the file based on the order's date
			String fileName = OrderDataGenerator.generateFileName(newOrder.getOrderDate());
			Path filePath = Paths.get(ordersFolderPath, fileName);

			// Check if the file exists
			if (!Files.exists(filePath)) {
				System.out.println("File does not exist"); // Print a message if the file does not exist
			}

			// Read all lines of the file
			List<String> lines = Files.readAllLines(filePath);

			// Look for the order to edit and replace it with the new order data
			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);
				String[] fields = line.split(",");
				int currentOrderId = Integer.parseInt(fields[0]);
				if (currentOrderId == orderId) {
					String newOrderData = orderId + "," + OrderDataGenerator.generateOrderData(newOrder);
					lines.set(i, newOrderData);
					break;
				}
			}

			// Write the lines back to the file
			Files.write(filePath, lines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

			return newOrder;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Order removeOrder(int orderNumber, LocalDate orderDate) {
		try {
			// Read the file based on the order's date
			String fileName = OrderDataGenerator.generateFileName(orderDate);
			Path filePath = Paths.get(ordersFolderPath, fileName);

			// Read all lines of the file
			List<String> lines = Files.readAllLines(filePath);

			// Look for the order to remove and remove it from the list
			Order removedOrder = null;
			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);
				String[] fields = line.split(",");
				int currentOrderNumber = Integer.parseInt(fields[0]);
				if (currentOrderNumber == orderNumber) {
					lines.remove(i);
					removedOrder = OrderDataGenerator.parseOrderData(fields);
					break;
				}
			}

			// Write the updated lines back to the file
			Files.write(filePath, lines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

			return removedOrder;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Order> retrieveOrders(LocalDate date) {
		String fileName = OrderDataGenerator.generateFileName(date);
		Path filePath = Paths.get(ordersFolderPath, fileName);

		List<Order> orders = new ArrayList<>();

		try {
			List<String> lines = Files.readAllLines(filePath);
			for (String line : lines) {
				String[] fields = line.split(",");
				Order order = OrderDataGenerator.parseOrderData(fields);
				orders.add(order);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return orders;
	}

	@Override
	public Order retrieveOrder(int orderNumber, LocalDate date) {
		String fileName = OrderDataGenerator.generateFileName(date);
		Path filePath = Paths.get(ordersFolderPath, fileName);

		try {
			List<String> lines = Files.readAllLines(filePath);
			for (String line : lines) {
				String[] fields = line.split(",");
				int currentOrderNumber = Integer.parseInt(fields[0]);
				if (currentOrderNumber == orderNumber) {
					return OrderDataGenerator.parseOrderData(fields);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
