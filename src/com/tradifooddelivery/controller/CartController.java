package com.tradifooddelivery.controller;

import java.util.ArrayList;
import java.util.List;
import com.tradifooddelivery.model.CartItem;
import com.tradifooddelivery.model.Product;

public class CartController {

    private List<CartItem> cartItems = new ArrayList<>();

    public void addToCart(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getName().equals(product.getName())) {
                item.incrementQuantity();
                return;
            }
        }
        cartItems.add(new CartItem(product));
    }

    public void removeFromCart(Product product) {
        cartItems.removeIf(item -> item.getProduct().getName().equals(product.getName()));
    }

    public void decreaseQuantity(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getName().equals(product.getName())) {
                item.decrementQuantity();
                return;
            }
        }
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public List<CartItem> getItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }
}
