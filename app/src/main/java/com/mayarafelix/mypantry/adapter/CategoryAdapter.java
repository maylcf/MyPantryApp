package com.mayarafelix.mypantry.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mayarafelix.mypantry.R;
import com.mayarafelix.mypantry.holder.CategoryHolder;
import com.mayarafelix.mypantry.model.Category;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder>
{
    private ArrayList<Category> categories;

    public CategoryAdapter(ArrayList<Category> categories)
    {
        this.categories = categories;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View categoryCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_category, parent, false);
        return new CategoryHolder(categoryCard);
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position)
    {
        final Category category = categories.get(position);
        holder.updateUI(category);
    }

    @Override
    public int getItemCount()
    {
        return categories.size();
    }
}
