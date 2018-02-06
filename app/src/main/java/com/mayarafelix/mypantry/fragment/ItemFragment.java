package com.mayarafelix.mypantry.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mayarafelix.mypantry.R;
import com.mayarafelix.mypantry.adapter.CategoryAdapter;
import com.mayarafelix.mypantry.adapter.ItemAdapter;
import com.mayarafelix.mypantry.model.Category;
import com.mayarafelix.mypantry.model.Item;
import com.mayarafelix.mypantry.task.CategoriesAsyncTask;
import com.mayarafelix.mypantry.task.ItemsAsyncTask;
import com.mayarafelix.mypantry.util.UrlManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by mayca on 2018-02-05.
 */

public class ItemFragment extends Fragment
{
    private ItemsAsyncTask itemsTask;
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private LinearLayoutManager layoutManager;
    private  ArrayList<Item> itemsArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        getActivity().setTitle("Items");
        setHasOptionsMenu(true);

        // Get View Elements
        itemsArrayList = new ArrayList<>();
        adapter = new ItemAdapter(itemsArrayList);
        recyclerView = (RecyclerView) view.findViewById(R.id.itemsList);
        layoutManager = new LinearLayoutManager(getActivity());

        // Recycler View
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true); // rows have the same size
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(), VERTICAL);
        recyclerView.addItemDecoration(decoration);

        // Download categories
        String dataUrl = UrlManager.itemsUrl;
        itemsTask = new ItemsAsyncTask(ItemFragment.this);
        itemsTask.execute(dataUrl);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_settings).setVisible(false);
        menu.findItem(R.id.action_add).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_add)
        {
            try
            {
                Class fragmentClass = ItemEditFragment.class;
                Fragment fragment = (Fragment) fragmentClass.newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentContent, fragment).commit();
                return true;
            }
            catch (Exception e)
            {
               return false;
            }
        }
        return false;
    }

    public void populateItems(JSONArray jsonArray)
    {
        if (jsonArray != null)
        {
            itemsArrayList.clear();

            for (int i = 0; i < jsonArray.length(); i++)
            {
                try
                {
                    JSONObject json = jsonArray.getJSONObject(i);
                    Item item = new Item(json);
                    itemsArrayList.add(item);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            adapter.notifyDataSetChanged();
        }
    }
}
