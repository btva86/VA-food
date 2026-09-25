package com.example.vafood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vafood.CartManager;
import com.example.vafood.MainActivity;
import com.example.vafood.R;
import com.example.vafood.model.Food;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    public interface OnFoodClick {
        void onClick(Food food);
    }

    private List<Food> foods;
    private final OnFoodClick listener;

    private final NumberFormat money = NumberFormat.getInstance(new Locale("vi", "VN"));

    public FoodAdapter(List<Food> foods, OnFoodClick listener) {
        this.foods = foods;
        this.listener = listener;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foods.get(position);

        holder.image.setImageResource(food.getImageResId());
        holder.name.setText(food.getName());
        holder.description.setText(food.getDescription());
        holder.category.setText(food.getCategory());
        holder.price.setText(money.format(food.getPrice()) + " VNĐ");

        holder.itemView.setOnClickListener(v -> listener.onClick(food));
        holder.detail.setOnClickListener(v -> listener.onClick(food));

        if (holder.addQuick != null) {
            holder.addQuick.setOnClickListener(v -> {
                CartManager.add(food, 1);
                Context context = v.getContext();
                Toast.makeText(context, "Đã thêm " + food.getName() + " vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                if (context instanceof MainActivity) {
                    ((MainActivity) context).updateCartCount();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView description;
        TextView category;
        TextView price;
        TextView detail;
        View addQuick;

        FoodViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.ivFood);
            name = view.findViewById(R.id.tvFoodName);
            description = view.findViewById(R.id.tvFoodDescription);
            category = view.findViewById(R.id.tvCategory);
            price = view.findViewById(R.id.tvPrice);
            detail = view.findViewById(R.id.btnDetail);
            addQuick = view.findViewById(R.id.btnAddQuick);
        }
    }
}
