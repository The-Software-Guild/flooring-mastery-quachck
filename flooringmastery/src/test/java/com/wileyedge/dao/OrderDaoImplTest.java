package com.wileyedge.dao;

import static org.junit.Assert.*;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.wileyedge.config.AppConfig;
import com.wileyedge.config.TestConfig;
import com.wileyedge.model.Area;
import com.wileyedge.model.Customer;
import com.wileyedge.model.Order;
import com.wileyedge.model.Product;
import com.wileyedge.model.State;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
public class OrderDaoImplTest {
	
	@Autowired
	private OrderDao orderDao;

	@Before
	public void setUp() throws Exception {
		deleteTestFiles();
	}

	private void deleteTestFiles() {
		String testOrdersFolderPath = "src/test/resources/test-data/TestOrders";

		File directory = new File(testOrdersFolderPath);
		if (directory.exists() && directory.isDirectory()) {
			File[] files = directory.listFiles();
			if (files != null) {
				for (File file : files) {
					file.delete();
				}
			}
		}
	}

	private Order createOrder(int orderId, String customerName, String stateAbbrv, String stateName, BigDecimal taxRate,
			String productType, BigDecimal costPerSqFt, BigDecimal laborCostPerSqFt, BigDecimal areaSize) {
		Customer customer = new Customer();
		customer.setName(customerName);

		State state = new State();
		state.setAbbrv(stateAbbrv);
		state.setName(stateName);
		state.setTaxRate(taxRate);

		Product product = new Product();
		product.setType(productType);
		product.setCostPerSquareFoot(costPerSqFt);
		product.setLaborCostPerSquareFoot(laborCostPerSqFt);

		Area area = new Area();
		area.setSize(areaSize);

		Order order = new Order();
		order.setOrderId(orderId);
		order.setCustomer(customer);
		order.setState(state);
		order.setProduct(product);
		order.setArea(area);
		order.setOrderDate(LocalDate.of(2025, Month.JUNE, 1));

		return order;
	}

	@Test
	public void testAddOrder() {
		// Arrange
		Order order = createOrder(2, "Acme", "TX", "Texas", new BigDecimal("25.00"), "Tile", new BigDecimal("3.50"),
				new BigDecimal("4.15"), new BigDecimal("249.00"));
		LocalDate testDate = LocalDate.of(2023, Month.JUNE, 6);
		order.setOrderDate(testDate);

		// Act
		Order addedOrder = orderDao.addOrder(order);

		// Assert
		assertNotNull(addedOrder);
		assertEquals(order, addedOrder);
	}

	@Test
	public void testEditOrder() {
		// Arrange
		Order order = createOrder(1, "Edit", "TX", "Texas", new BigDecimal("25.00"), "Tile", new BigDecimal("3.50"),
				new BigDecimal("4.15"), new BigDecimal("249.00"));
		LocalDate testDate = LocalDate.of(2025, Month.JUNE, 6);
		order.setOrderDate(testDate);

		// Add the order
		Order addedOrder = orderDao.addOrder(order);

		// Ensure the order was added
		assertNotNull(addedOrder);

		// Edit the order
		Order editedOrder = createOrder(addedOrder.getOrderId(), "New Company", "CA", "California",
				new BigDecimal("25.00"), "Wood", new BigDecimal("5.15"), new BigDecimal("4.75"),
				new BigDecimal("300.00"));
		editedOrder.setOrderDate(testDate);

		// Edit the order
		Order result = orderDao.editOrder(addedOrder.getOrderId(), editedOrder);

		// Ensure the order was edited
		assertNotNull(result);

		// ... add assertions to verify the order was edited correctly
		assertEquals(editedOrder.getCustomer().getName(), result.getCustomer().getName());
		assertEquals(editedOrder.getState().getAbbrv(), result.getState().getAbbrv());
		assertEquals(editedOrder.getProduct().getType(), result.getProduct().getType());
		assertEquals(0, editedOrder.getArea().getSize().compareTo(result.getArea().getSize()));
		assertEquals(0, editedOrder.getMaterialCost().compareTo(result.getMaterialCost()));
		assertEquals(0, editedOrder.getLaborCost().compareTo(result.getLaborCost()));
		assertEquals(0, editedOrder.getTax().compareTo(result.getTax()));
		assertEquals(0, editedOrder.getTotal().compareTo(result.getTotal()));
	}

	@Test
	public void testRemoveOrder() {
		// Arrange
		Order order = createOrder(1, "remove", "TX", "Texas", new BigDecimal("25.00"), "Carpet", new BigDecimal("2.25"),
				new BigDecimal("2.10"), new BigDecimal("500.00"));
		LocalDate testDate = LocalDate.of(2025, Month.JANUARY, 6);
		order.setOrderDate(testDate);

		// Add the order
		Order addedOrder = orderDao.addOrder(order);

		// Ensure the order was added
		assertNotNull(addedOrder);

		// Act
		Order removedOrder = orderDao.removeOrder(addedOrder.getOrderId(), testDate);

		// Assert
		assertNotNull(removedOrder);
		assertEquals(addedOrder.getCustomer().getName(), removedOrder.getCustomer().getName());
		assertEquals(addedOrder.getState().getAbbrv(), removedOrder.getState().getAbbrv());
		assertEquals(addedOrder.getProduct().getType(), removedOrder.getProduct().getType());
		assertEquals(0, addedOrder.getArea().getSize().compareTo(removedOrder.getArea().getSize()));
		assertEquals(0, addedOrder.getMaterialCost().compareTo(removedOrder.getMaterialCost()));
		assertEquals(0, addedOrder.getLaborCost().compareTo(removedOrder.getLaborCost()));
		assertEquals(0, addedOrder.getTax().compareTo(removedOrder.getTax()));
		assertEquals(0, addedOrder.getTotal().compareTo(removedOrder.getTotal()));

		// Verify the order is no longer retrievable
		List<Order> ordersForTestDate = orderDao.retrieveOrders(testDate);
		assertFalse(ordersForTestDate.contains(removedOrder));
	}

	@Test
	public void testRetrieveOrders() {
		// Arrange
		LocalDate date = LocalDate.of(2025, Month.JUNE, 1);

		// Create some orders and add them to the system
		Order order1 = createOrder(1, "Acme", "TX", "Texas", new BigDecimal("25.00"), "Tile", new BigDecimal("3.50"),
				new BigDecimal("4.15"), new BigDecimal("249.00"));
		Order order2 = createOrder(2, "Beta", "CA", "California", new BigDecimal("25.00"), "Wood",
				new BigDecimal("5.15"), new BigDecimal("4.75"), new BigDecimal("300.00"));
		orderDao.addOrder(order1);
		orderDao.addOrder(order2);

		// Act
		List<Order> retrievedOrders = orderDao.retrieveOrders(date);

		// Assert
		assertNotNull(retrievedOrders);
		assertEquals(2, retrievedOrders.size());

		boolean order1Exists = false;
		for (Order order : retrievedOrders) {
			if (order.getOrderId() == order1.getOrderId()) {
				order1Exists = true;
				break;
			}
		}
		boolean order2Exists = false;
		for (Order order : retrievedOrders) {
			if (order.getOrderId() == order2.getOrderId()) {
				order2Exists = true;
				break;
			}
		}
		assertTrue(order1Exists && order2Exists);
	}
}
