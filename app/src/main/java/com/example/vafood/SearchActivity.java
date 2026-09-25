package com.example.vafood;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vafood.adapter.FoodAdapter;
import com.example.vafood.model.Food;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    private EditText input;
    private FoodAdapter adapter;
    private TextView empty;

    private final List<Food> foods =
            FoodRepository.getFoods();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_search
        );

        input = findViewById(
                R.id.etSearch
        );

        empty = findViewById(
                R.id.tvSearchEmpty
        );

        RecyclerView recycler =
                findViewById(
                        R.id.recyclerSearch
                );

        recycler.setLayoutManager(
                new LinearLayoutManager(this)
        );

        adapter = new FoodAdapter(
                new ArrayList<>(),
                this::openDetail
        );

        recycler.setAdapter(adapter);

        MaterialButton search =
                findViewById(
                        R.id.btnDoSearch
                );

        search.setOnClickListener(
                v -> searchFood()
        );

        findViewById(
                R.id.btnBack
        ).setOnClickListener(
                v -> finish()
        );

        input.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after
                    ) {
                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count
                    ) {
                        searchFood();
                    }

                    @Override
                    public void afterTextChanged(
                            Editable s
                    ) {
                    }
                }
        );
    }

    private void searchFood() {

        String keyword =
                input.getText()
                        .toString()
                        .trim()
                        .toLowerCase(
                                Locale.ROOT
                        );

        List<Food> result =
                new ArrayList<>();

        for (Food food : foods) {

            if (
                    keyword.isEmpty()
                            || food.getName()
                            .toLowerCase(
                                    Locale.ROOT
                            )
                            .contains(keyword)
            ) {

                result.add(food);
            }
        }

        adapter.setFoods(result);

        empty.setVisibility(
                result.isEmpty()
                        ? View.VISIBLE
                        : View.GONE
        );
    }

    private void openDetail(Food food) {

        Intent intent =
                new Intent(
                        this,
                        DetailActivity.class
                );

        intent.putExtra(
                "food_id",
                food.getId()
        );

        startActivity(intent);
    }
}