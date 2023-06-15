package com.wileyedge.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.wileyedge.dao.OrderDao;
import com.wileyedge.exception.InvalidOrderException;
import com.wileyedge.model.Order;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Mock
	private OrderDao orderDao;

	@InjectMocks
	private OrderServiceImpl orderService;

	@Test
	public void testAddOrder() {
		// Create a mock Order object
		Order order = new Order();

		// Stub the behavior of the OrderDao's addOrder method
		when(orderDao.addOrder(order)).thenReturn(order);

		// Call the service method
		Order addedOrder = null;
		try {
			addedOrder = orderService.addOrder(order);
		} catch (InvalidOrderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Verify that the OrderDao's addOrder method was called with the correct
		// argument
		verify(orderDao).addOrder(order);

		// Assert that the returned Order object is the same as the one returned by the
		// mock
		assertEquals(order, addedOrder);
	}

	@Test
	public void testGetOrdersByDate() {
		// Create a mock LocalDate object
		LocalDate date = LocalDate.now();

		// Create a list of mock Order objects
		List<Order> orders = new ArrayList<>();
		orders.add(new Order());
		orders.add(new Order());

		// Stub the behavior of the OrderDao's retrieveOrders method
		when(orderDao.retrieveOrders(date)).thenReturn(orders);

		// Call the service method
		List<Order> retrievedOrders = orderService.getOrdersByDate(date);

		// Verify that the OrderDao's retrieveOrders method was called with the correct
		// argument
		verify(orderDao).retrieveOrders(date);

		// Assert that the returned list of orders is the same as the one returned by
		// the mock
		assertEquals(orders, retrievedOrders);
	}

	@Test
	public void testEditOrder() {
		// Create a mock Order object
		Order order = new Order();
		int orderNumber = 1;

		// Stub the behavior of the OrderDao's editOrder method
		when(orderDao.editOrder(orderNumber, order)).thenReturn(order);

		// Call the service method
		Order editedOrder = orderService.editOrder(orderNumber, order);

		// Verify that the OrderDao's editOrder method was called with the correct
		// arguments
		verify(orderDao).editOrder(orderNumber, order);

		// Assert that the returned Order object is the same as the one returned by the
		// mock
		assertEquals(order, editedOrder);
	}

	@Test
	public void testRetrieveOrders() {
		// Create a mock LocalDate object
		LocalDate date = LocalDate.now();

		// Create a list of mock Order objects
		List<Order> orders = new ArrayList<>();
		orders.add(new Order());
		orders.add(new Order());

		// Stub the behavior of the OrderDao's retrieveOrders method
		when(orderDao.retrieveOrders(date)).thenReturn(orders);

		// Call the service method
		List<Order> retrievedOrders = orderService.retrieveOrders(date);

		// Verify that the OrderDao's retrieveOrders method was called with the correct
		// argument
		verify(orderDao).retrieveOrders(date);

		// Assert that the returned list of orders is the same as the one returned by
		// the mock
		assertEquals(orders, retrievedOrders);
	}

}
