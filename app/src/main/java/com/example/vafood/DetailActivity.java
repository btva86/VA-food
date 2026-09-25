package com.example.vafood;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vafood.model.Food;
import com.google.android.material.button.MaterialButton;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private Food food;

    private int quantity = 1;

    private TextView tvQty;
    private TextView tvTotal;

    private SharedPreferences preferences;

    private final NumberFormat money =
            NumberFormat.getInstance(
                    new Locale("vi", "VN")
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_detail
        );

        preferences =
                getSharedPreferences(
                        "food_prefs",
                        MODE_PRIVATE
                );

        int id =
                getIntent().getIntExtra(
                        "food_id",
                        -1
                );

        food = FoodRepository.findById(id);

        if (food == null) {
            finish();
            return;
        }

        ImageButton back =
                findViewById(
                        R.id.btnBackDetail
                );

        back.setOnClickListener(
                v -> finish()
        );

        android.widget.ImageView image =
                findViewById(
                        R.id.ivDetail
                );

        image.setImageResource(
                food.getImageResId()
        );

        TextView name =
                findViewById(
                        R.id.tvDetailName
                );

        name.setText(
                food.getName()
        );

        TextView description =
                findViewById(
                        R.id.tvDetailDescription
                );

        description.setText(
                food.getDescription()
        );

        TextView price =
                findViewById(
                        R.id.tvDetailPrice
                );

        price.setText(
                money.format(
                        food.getPrice()
                ) + " VNĐ"
        );

        TextView ingredients =
                findViewById(
                        R.id.tvIngredients
                );

        ingredients.setText(
                food.getIngredients()
        );

        ImageButton favorite =
                findViewById(
                        R.id.btnFavorite
                );

        updateFavoriteIcon(favorite);

        favorite.setOnClickListener(v -> {

            boolean next =
                    !preferences.getBoolean(
                            "fav_" + food.getId(),
                            false
                    );

            preferences.edit()
                    .putBoolean(
                            "fav_" + food.getId(),
                            next
                    )
                    .apply();

            updateFavoriteIcon(favorite);

            Toast.makeText(
                    this,
                    next
                            ? "Đã thêm vào yêu thích"
                            : "Đã bỏ khỏi yêu thích",
                    Toast.LENGTH_SHORT
            ).show();
        });

        tvQty =
                findViewById(
                        R.id.tvQuantity
                );

        tvTotal =
                findViewById(
                        R.id.tvDetailTotal
                );

        findViewById(
                R.id.btnQtyMinus
        ).setOnClickListener(v -> {

            if (quantity > 1) {
                quantity--;
                updateTotal();
            }
        });

        findViewById(
                R.id.btnQtyPlus
        ).setOnClickListener(v -> {

            quantity++;
            updateTotal();
        });

        MaterialButton order =
                findViewById(
                        R.id.btnOrder
                );

        order.setOnClickListener(v -> {

            CartManager.add(
                    food,
                    quantity
            );

            Toast.makeText(
                    this,
                    "Đặt món thành công! Đã thêm vào giỏ hàng.",
                    Toast.LENGTH_SHORT
            ).show();
        });

        updateTotal();
    }

    private void updateTotal() {

        tvQty.setText(
                String.valueOf(quantity)
        );

        tvTotal.setText(
                money.format(
                        food.getPrice()
                                * quantity
                ) + " VNĐ"
        );
    }

    private void updateFavoriteIcon(
            ImageButton button
    ) {

        boolean favorite =
                preferences.getBoolean(
                        "fav_" + food.getId(),
                        false
                );

        button.setImageResource(
                favorite
                        ? android.R.drawable.btn_star_big_on
                        : android.R.drawable.btn_star_big_off
        );
    }
}