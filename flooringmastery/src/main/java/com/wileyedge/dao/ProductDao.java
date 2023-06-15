package com.wileyedge.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wileyedge.model.Product;

@Repository
public interface ProductDao {
	List<Product> getAllProducts();
}
