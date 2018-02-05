package com.mayarafelix.mypantry.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mayarafelix.mypantry.R;
import com.mayarafelix.mypantry.model.Category;

/**
 * Created by mayca on 2018-02-05.
 */

public class CategoryHolder extends RecyclerView.ViewHolder
{
    private TextView name;

    public CategoryHolder(View itemView)
    {
        super(itemView);

        this.name = (TextView) itemView.findViewById(R.id.cardCategoryName);
    }

    public void updateUI(Category category)
    {
        name.setText(category.getName());
    }
}
