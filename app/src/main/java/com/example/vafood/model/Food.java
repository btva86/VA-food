package com.example.vafood.model;

import java.io.Serializable;

public class Food implements Serializable {

    private final int id;
    private final String name;
    private final String description;
    private final String ingredients;
    private final String category;
    private final int price;
    private final int imageResId;

    public Food(
            int id,
            String name,
            String description,
            String ingredients,
            String category,
            int price,
            int imageResId
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.category = category;
        this.price = price;
        this.imageResId = imageResId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }
}
