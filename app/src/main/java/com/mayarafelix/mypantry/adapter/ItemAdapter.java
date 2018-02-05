package com.mayarafelix.mypantry.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mayarafelix.mypantry.R;
import com.mayarafelix.mypantry.holder.ItemHolder;
import com.mayarafelix.mypantry.model.Item;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder>
{
    private ArrayList<Item> items;

    public ItemAdapter(ArrayList<Item> items)
    {
        this.items = items;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ItemHolder(card);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position)
    {
        final Item item = items.get(position);
        holder.updateUI(item);
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }
}
