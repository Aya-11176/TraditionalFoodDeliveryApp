package com.tradifooddelivery.controller;

import com.tradifooddelivery.model.CartItem;
import com.tradifooddelivery.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartController {
    private final List<CartItem> cart = new ArrayList<>();

    public void addToCart(Product product) {
        for (CartItem item : cart) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        cart.add(new CartItem(product, 1));
    }

    public List<CartItem> getCart() {
        return cart;
    }

    public void removeItem(Product product) {
        cart.removeIf(i -> i.getProduct().getId() == product.getId());
    }

    public void clearCart() {
        cart.clear();
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : cart) total += item.getTotalPrice();
        return total;
    }
}
