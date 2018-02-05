package com.mayarafelix.mypantry.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.mayarafelix.mypantry.R;
import com.mayarafelix.mypantry.model.Item;

public class ItemHolder extends RecyclerView.ViewHolder
{
    private ImageView imageView;
    private TextView name;
    private TextView category;

    public ItemHolder(View itemView)
    {
        super(itemView);

        this.name      = itemView.findViewById(R.id.cardItemName);
        this.category  = itemView.findViewById(R.id.cardItemCategory);
        this.imageView = itemView.findViewById(R.id.cardItemImage);
    }

    public void updateUI(Item item)
    {
        name.setText(item.getName());
        category.setText(item.getCategory());
    }
}
