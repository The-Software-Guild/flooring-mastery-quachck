package com.wileyedge.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Repository;

import com.wileyedge.model.Product;

@Repository
public class ProductDaoImpl implements ProductDao {

    private static final String PRODUCT_FILE = "src/main/resources/production-data/Data/Products.txt";

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        File file = new File(PRODUCT_FILE);
        
        try (Scanner scanner = new Scanner(file)) {
            // Ignore the header line
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // assuming each line is 'productType,CostPerSquareFoot,LaborCostPerSquareFoot'
                String[] productData = line.split(",");
                Product product = new Product();
                product.setType(productData[0]);
                product.setCostPerSquareFoot(new BigDecimal(productData[1]));
                product.setLaborCostPerSquareFoot(new BigDecimal(productData[2]));
                products.add(product);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return products;
    }
}


