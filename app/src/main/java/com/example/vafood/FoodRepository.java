package com.example.vafood;

import com.example.vafood.model.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodRepository {

    private static final List<Food> FOODS = new ArrayList<>();

    static {
        FOODS.add(new Food(
                1,
                "Hamburger bò",
                "Burger bò mềm, đậm vị với rau tươi và sốt đặc biệt.",
                "Bánh mì, bò xay, xà lách, cà chua, phô mai, sốt burger",
                "Đồ ăn nhanh",
                55000,
                R.drawable.burger
        ));

        FOODS.add(new Food(
                2,
                "Pizza hải sản",
                "Pizza hải sản phủ phô mai béo ngậy, đế giòn vừa.",
                "Tôm, mực, phô mai, sốt cà chua, bột mì",
                "Đồ ăn nhanh",
                120000,
                R.drawable.pizza
        ));

        FOODS.add(new Food(
                3,
                "Mì cay",
                "Mì cay nước dùng đậm đà, có thể chọn mức cay.",
                "Mì, xúc xích, nấm, rau, ớt, nước dùng",
                "Món Việt",
                65000,
                R.drawable.noodles
        ));

        FOODS.add(new Food(
                4,
                "Gà rán",
                "Gà rán giòn bên ngoài, mềm mọng bên trong.",
                "Thịt gà, bột chiên, gia vị, sốt",
                "Đồ ăn nhanh",
                75000,
                R.drawable.chicken
        ));

        FOODS.add(new Food(
                5,
                "Trà sữa",
                "Trà sữa thơm béo, topping trân châu dai ngon.",
                "Trà, sữa, đường, trân châu",
                "Đồ uống",
                35000,
                R.drawable.milktea
        ));

        FOODS.add(new Food(
                6,
                "Phở bò",
                "Phở bò truyền thống với nước dùng thơm ngon.",
                "Bánh phở, thịt bò, hành, rau thơm, nước dùng",
                "Món Việt",
                60000,
                R.drawable.pho
        ));

        FOODS.add(new Food(
                7,
                "Salad rau củ",
                "Salad tươi mát phù hợp cho bữa ăn nhẹ.",
                "Xà lách, cà chua, dưa chuột, ngô, sốt mè",
                "Món Việt",
                45000,
                R.drawable.salad
        ));

        FOODS.add(new Food(
                8,
                "Bít tết bò",
                "Thịt bò áp chảo thơm mềm dùng kèm rau củ.",
                "Thịt bò, bơ, tỏi, tiêu, rau củ",
                "Món Việt",
                145000,
                R.drawable.steak
        ));
    }

    public static List<Food> getFoods() {
        return new ArrayList<>(FOODS);
    }

    public static Food findById(int id) {
        for (Food food : FOODS) {
            if (food.getId() == id) {
                return food;
            }
        }
        return null;
    }
}
