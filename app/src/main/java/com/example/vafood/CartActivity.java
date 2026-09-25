package com.example.vafood;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vafood.adapter.CartAdapter;
import com.google.android.material.button.MaterialButton;

import java.text.NumberFormat;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private CartAdapter adapter;

    private TextView total;
    private TextView empty;

    private final NumberFormat money =
            NumberFormat.getInstance(
                    new Locale("vi", "VN")
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_cart
        );

        findViewById(
                R.id.btnBackCart
        ).setOnClickListener(
                v -> finish()
        );

        empty =
                findViewById(
                        R.id.tvCartEmpty
                );

        total =
                findViewById(
                        R.id.tvCartGrandTotal
                );

        RecyclerView recycler =
                findViewById(
                        R.id.recyclerCart
                );

        recycler.setLayoutManager(
                new LinearLayoutManager(this)
        );

        adapter =
                new CartAdapter(
                        CartManager.getItems(),
                        this::refresh
                );

        recycler.setAdapter(adapter);

        MaterialButton checkout =
                findViewById(
                        R.id.btnCheckout
                );

        checkout.setOnClickListener(
                v -> checkout()
        );

        findViewById(
                R.id.btnClearCart
        ).setOnClickListener(v -> {

            CartManager.clear();

            refresh();
        });

        refresh();
    }

    private void refresh() {

        adapter.refresh();

        int totalMoney =
                CartManager.getTotal();

        total.setText(
                money.format(
                        totalMoney
                ) + " VNĐ"
        );

        boolean isEmpty =
                CartManager.getItems()
                        .isEmpty();

        empty.setVisibility(
                isEmpty
                        ? View.VISIBLE
                        : View.GONE
        );

        findViewById(
                R.id.btnCheckout
        ).setEnabled(!isEmpty);
    }

    private void checkout() {

        new AlertDialog.Builder(this)
                .setTitle(
                        "Xác nhận đặt món"
                )
                .setMessage(
                        "Tổng thanh toán: "
                                + money.format(
                                CartManager.getTotal()
                        )
                                + " VNĐ\n\n"
                                + "Bạn có chắc muốn đặt các món trong giỏ?"
                )
                .setNegativeButton(
                        "Hủy",
                        null
                )
                .setPositiveButton(
                        "Đặt món",
                        (dialog, which) -> {

                            CartManager.clear();

                            refresh();

                            Toast.makeText(
                                    this,
                                    "Đặt món thành công!",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                )
                .show();
    }
}