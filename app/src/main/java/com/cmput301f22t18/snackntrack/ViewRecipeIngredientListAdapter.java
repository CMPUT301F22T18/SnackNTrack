package com.cmput301f22t18.snackntrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cmput301f22t18.snackntrack.models.Ingredient;

import java.util.ArrayList;

public class ViewRecipeIngredientListAdapter extends ArrayAdapter {
    private final ArrayList<Ingredient> ingredients;
    private final Context context;

    public ViewRecipeIngredientListAdapter(Context context, ArrayList<Ingredient> ingredients) {
        super(context, 0, ingredients);
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content_view_recipe_ingredients, parent, false);
        }

        Ingredient ingredient = ingredients.get(position);

        TextView ingredientName = view.findViewById(R.id.view_recipe_ingredient_name);
        TextView ingredientCategory = view.findViewById(R.id.view_recipe_ingredient_category);
        TextView ingredientAmount = view.findViewById(R.id.view_recipe_ingredient_amount);

        ingredientName.setText(ingredient.getDescription());
        ingredientCategory.setText(ingredient.getCategory());
        String amount = ingredient.getAmount() + " " + ingredient.getUnit();
        ingredientAmount.setText(amount);

        return view;
    }
}
