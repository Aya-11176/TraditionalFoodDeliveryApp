package com.tradifooddelivery.controller;

import com.tradifooddelivery.model.Product;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class to manage data and actions between model and view.
 * For now, it's simple — will grow in next sessions.
 */
public class AppController {

    private List<Product> products = new ArrayList<>();

    public AppController() {
        // Example traditional dishes (sample data)
        products.add(new Product(1, "Couscous", 850, "Main Dish", "images/couscous.jpg"));
        products.add(new Product(2, "Chakhchoukha", 900, "Main Dish", "images/chakhchoukha.jpg"));
        products.add(new Product(3, "Makroud", 300, "Dessert", "images/makroud.jpg"));
    }

    public List<Product> getProducts() {
        return products;
    }
}
