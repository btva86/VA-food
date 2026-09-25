package com.example.vafood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vafood.adapter.FoodAdapter;
import com.example.vafood.model.Food;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FoodAdapter adapter;
    private Spinner spinnerSort;
    private TextView tvEmpty;
    private TextView tvCartCount;

    private final List<Food> allFoods = FoodRepository.getFoods();

    private String selectedCategory = "Tất cả";

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("food_prefs", MODE_PRIVATE);

        RecyclerView recyclerFoods = findViewById(R.id.recyclerFoods);

        recyclerFoods.setLayoutManager(
                new LinearLayoutManager(this)
        );

        adapter = new FoodAdapter(
                new ArrayList<>(allFoods),
                this::openDetail
        );

        recyclerFoods.setAdapter(adapter);

        tvEmpty = findViewById(R.id.tvEmpty);
        tvCartCount = findViewById(R.id.tvCartCount);
        spinnerSort = findViewById(R.id.spinnerSort);

        setupSort();
        setupCategories();

        MaterialButton btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SearchActivity.class
            );

            startActivity(intent);
        });

        findViewById(R.id.btnCart).setOnClickListener(v -> {
            startActivity(
                    new Intent(
                            MainActivity.this,
                            CartActivity.class
                    )
            );
        });

        findViewById(R.id.btnFavorites).setOnClickListener(
                v -> showFavorites()
        );

        updateCartCount();
    }

    private void setupSort() {

        String[] options = {
                "Sắp xếp mặc định",
                "Giá thấp → cao",
                "Giá cao → thấp"
        };

        ArrayAdapter<String> sortAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        options
                );

        spinnerSort.setAdapter(sortAdapter);

        spinnerSort.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            View view,
                            int position,
                            long id
                    ) {
                        applyFilter();
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent
                    ) {
                    }
                }
        );
    }

    private void setupCategories() {

        ChipGroup group = findViewById(
                R.id.categoryGroup
        );

        String[] categories = {
                "Tất cả",
                "Đồ ăn nhanh",
                "Món Việt",
                "Đồ uống"
        };

        for (String category : categories) {

            Chip chip = new Chip(this);

            chip.setText(category);
            chip.setCheckable(true);

            if (category.equals("Tất cả")) {
                chip.setChecked(true);
            }

            chip.setOnClickListener(v -> {

                selectedCategory = category;

                for (int i = 0; i < group.getChildCount(); i++) {

                    Chip current =
                            (Chip) group.getChildAt(i);

                    current.setChecked(
                            current == v
                    );
                }

                applyFilter();
            });

            group.addView(chip);
        }
    }

    private void applyFilter() {

        List<Food> result = new ArrayList<>();

        for (Food food : allFoods) {

            if (
                    selectedCategory.equals("Tất cả")
                            || food.getCategory()
                            .equals(selectedCategory)
            ) {
                result.add(food);
            }
        }

        int sortPosition =
                spinnerSort.getSelectedItemPosition();

        if (sortPosition == 1) {

            result.sort(
                    Comparator.comparingInt(
                            Food::getPrice
                    )
            );

        } else if (sortPosition == 2) {

            result.sort(
                    (a, b) ->
                            Integer.compare(
                                    b.getPrice(),
                                    a.getPrice()
                            )
            );
        }

        adapter.setFoods(result);

        tvEmpty.setText(
                "Không có món ăn phù hợp."
        );

        tvEmpty.setVisibility(
                result.isEmpty()
                        ? View.VISIBLE
                        : View.GONE
        );
    }

    private void showFavorites() {

        List<Food> result = new ArrayList<>();

        for (Food food : allFoods) {

            if (
                    preferences.getBoolean(
                            "fav_" + food.getId(),
                            false
                    )
            ) {
                result.add(food);
            }
        }

        adapter.setFoods(result);

        tvEmpty.setText(
                "Chưa có món yêu thích."
        );

        tvEmpty.setVisibility(
                result.isEmpty()
                        ? View.VISIBLE
                        : View.GONE
        );
    }

    private void openDetail(Food food) {

        Intent intent = new Intent(
                this,
                DetailActivity.class
        );

        intent.putExtra(
                "food_id",
                food.getId()
        );

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartCount();
    }

    public void updateCartCount() {

        tvCartCount.setText(
                String.valueOf(
                        CartManager.getCount()
                )
        );
    }
}
