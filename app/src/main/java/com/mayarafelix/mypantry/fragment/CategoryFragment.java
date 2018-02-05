package com.mayarafelix.mypantry.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mayarafelix.mypantry.CategoryActivity;
import com.mayarafelix.mypantry.R;
import com.mayarafelix.mypantry.adapter.CategoryAdapter;
import com.mayarafelix.mypantry.model.Category;
import com.mayarafelix.mypantry.task.CategoriesAsyncTask;
import com.mayarafelix.mypantry.util.UrlManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryFragment extends Fragment
{
    private CategoriesAsyncTask categoriesAsyncTask;
    private CategoryAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private  ArrayList<Category> categoriesArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        setHasOptionsMenu(true);

        categoriesArrayList = new ArrayList<>();
        adapter = new CategoryAdapter(categoriesArrayList);
        recyclerView = (RecyclerView) view.findViewById(R.id.categoryList);
        layoutManager = new LinearLayoutManager(getActivity());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true); // rows have the same size
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        String dataUrl = UrlManager.categoriesURL;
        categoriesAsyncTask = new CategoriesAsyncTask(CategoryFragment.this);
        categoriesAsyncTask.execute(dataUrl);

        getActivity().setTitle("Category");

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
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

            final EditText edittext = new EditText(getActivity());
            alert.setMessage("Enter Your Message");
            alert.setTitle("Enter Your Title");
            alert.setView(edittext, (int)(19dpi), (int)(5*dpi), (int)(14*dpi), (int)(5*dpi) );

            alert.setPositiveButton("Yes Option", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //What ever you want to do with the value
                    Editable YouEditTextValue = edittext.getText();
                    //OR
                    String c = edittext.getText().toString();
                    Log.w("May", c);
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                }
            });

            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void populateCategories(JSONArray jsonArray)
    {
        if (jsonArray != null)
        {
            categoriesArrayList.clear();

            for (int i = 0; i < jsonArray.length(); i++)
            {
                try
                {
                    JSONObject json = jsonArray.getJSONObject(i);
                    Category category = new Category(json);
                    categoriesArrayList.add(category);
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
