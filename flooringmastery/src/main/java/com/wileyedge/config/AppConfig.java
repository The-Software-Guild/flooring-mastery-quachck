package com.wileyedge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.wileyedge.controller.OrderController;
import com.wileyedge.dao.OrderDao;
import com.wileyedge.dao.OrderDaoImpl;
import com.wileyedge.dao.ProductDao;
import com.wileyedge.dao.ProductDaoImpl;
import com.wileyedge.dao.StateDao;
import com.wileyedge.dao.StateDaoImpl;
import com.wileyedge.service.OrderService;
import com.wileyedge.service.OrderServiceImpl;
import com.wileyedge.view.OrderView;
import com.wileyedge.view.UserIO;
import com.wileyedge.view.UserIOConsoleImpl;

@Configuration
@PropertySource("classpath:application-prod.properties")
public class AppConfig {
    
	@Bean
	public OrderDao orderDao() {
		return new OrderDaoImpl();
	}

	@Bean
	public ProductDao productDao() {
		return new ProductDaoImpl();
	}

	@Bean
	public StateDao stateDao() {
		return new StateDaoImpl();
	}

	@Bean
	public OrderService orderService() {
		return new OrderServiceImpl(orderDao(), stateDao(), productDao());
	}

	@Bean
	public UserIO userIO() {
		return new UserIOConsoleImpl();
	}

	@Bean
	public OrderView orderView() {
		return new OrderView(userIO());
	}

	@Bean
	public OrderController orderController() {
		return new OrderController(orderService(), orderView());
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	    return new PropertySourcesPlaceholderConfigurer();
	}

}
