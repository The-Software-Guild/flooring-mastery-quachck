package com.wileyedge;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.wileyedge.config.AppConfig;
import com.wileyedge.controller.OrderController;
import com.wileyedge.view.OrderView;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.wileyedge")
public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        OrderController orderController = context.getBean(OrderController.class);
        orderController.run();
    }
}


