package com.example.vafood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vafood.CartManager;
import com.example.vafood.R;
import com.example.vafood.model.CartItem;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Holder> {

    public interface OnChanged {
        void changed();
    }

    private List<CartItem> items;
    private final OnChanged listener;

    private final NumberFormat money = NumberFormat.getInstance(new Locale("vi", "VN"));

    public CartAdapter(List<CartItem> items, OnChanged listener) {
        this.items = items;
        this.listener = listener;
    }

    public void refresh() {
        items = CartManager.getItems();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        CartItem item = items.get(position);

        holder.image.setImageResource(item.getFood().getImageResId());
        holder.name.setText(item.getFood().getName());
        holder.price.setText(money.format(item.getFood().getPrice()) + " VNĐ");
        holder.qty.setText(String.valueOf(item.getQuantity()));
        holder.total.setText(money.format(item.getTotal()) + " VNĐ");

        holder.minus.setOnClickListener(v -> {
            CartManager.decrease(item);
            refresh();
            listener.changed();
        });

        holder.plus.setOnClickListener(v -> {
            CartManager.increase(item);
            refresh();
            listener.changed();
        });

        holder.remove.setOnClickListener(v -> {
            CartManager.remove(item);
            refresh();
            listener.changed();
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView price;
        TextView qty;
        TextView total;
        View minus;
        View plus;
        View remove;

        Holder(View view) {
            super(view);
            image = view.findViewById(R.id.ivCartFood);
            name = view.findViewById(R.id.tvCartName);
            price = view.findViewById(R.id.tvCartPrice);
            qty = view.findViewById(R.id.tvCartQty);
            total = view.findViewById(R.id.tvCartTotal);
            minus = view.findViewById(R.id.btnMinus);
            plus = view.findViewById(R.id.btnPlus);
            remove = view.findViewById(R.id.btnRemove);
        }
    }
}
