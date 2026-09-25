package com.example.vafood;

import com.example.vafood.model.CartItem;
import com.example.vafood.model.Food;

import java.util.ArrayList;
import java.util.List;

public class CartManager {

    private static final List<CartItem> items = new ArrayList<>();

    public static void add(Food food, int quantity) {
        for (CartItem item : items) {
            if (item.getFood().getId() == food.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(food, quantity));
    }

    public static void increase(CartItem item) {
        item.setQuantity(item.getQuantity() + 1);
    }

    public static void decrease(CartItem item) {
        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
        } else {
            remove(item);
        }
    }

    public static void remove(CartItem item) {
        items.remove(item);
    }

    public static void clear() {
        items.clear();
    }

    public static List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    public static int getCount() {
        int count = 0;
        for (CartItem item : items) {
            count += item.getQuantity();
        }
        return count;
    }

    public static int getTotal() {
        int total = 0;
        for (CartItem item : items) {
            total += item.getTotal();
        }
        return total;
    }
}
