package com.example.finallabh071241050.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.finallabh071241050.R;
import com.example.finallabh071241050.model.Meal;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private List<Meal> mealList;

    public MealAdapter(List<Meal> mealList) {
        this.mealList = mealList;
    }

    public void updateData(List<Meal> newMealList) {
        this.mealList = newMealList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = mealList.get(position);
        holder.tvName.setText(meal.strMeal);
        holder.tvCategory.setText(meal.strCategory != null ? meal.strCategory : "Resep Lezat");

        Glide.with(holder.itemView.getContext())
                .load(meal.strMealThumb)
                .into(holder.imgMeal);

        // PERBAIKAN: Gunakan ID tujuan langsung (R.id.detailFragment) agar bisa diakses dari mana saja
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("EXTRA_ID", meal.idMeal);
            Navigation.findNavController(v).navigate(R.id.detailFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return mealList != null ? mealList.size() : 0;
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMeal;
        TextView tvName, tvCategory;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMeal = itemView.findViewById(R.id.img_meal);
            tvName = itemView.findViewById(R.id.tv_meal_name);
            tvCategory = itemView.findViewById(R.id.tv_category);
        }
    }
}