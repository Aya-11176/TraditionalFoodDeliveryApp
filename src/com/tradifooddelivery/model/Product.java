package com.tradifooddelivery.model;

/**
 * Represents a traditional food product in the app.
 * This class belongs to the 'Model' layer.
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private String category;
    private String imagePath;

    public Product(int id, String name, double price, String category, String imagePath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.imagePath = imagePath;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public String getImagePath() { return imagePath; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setCategory(String category) { this.category = category; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    @Override
    public String toString() {
        return name + " (" + category + ") - " + price + " DA";
    }
}
