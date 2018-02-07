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
import com.mayarafelix.mypantry.model.Category;
import com.mayarafelix.mypantry.task.CategoriesAsyncTask;
import com.mayarafelix.mypantry.util.UrlManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.widget.LinearLayout.VERTICAL;

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
        getActivity().setTitle("Category");
        setHasOptionsMenu(true);

        // Get View Elements
        categoriesArrayList = new ArrayList<>();
        adapter = new CategoryAdapter(categoriesArrayList);
        recyclerView = (RecyclerView) view.findViewById(R.id.categoryList);
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
        String dataUrl = UrlManager.categoriesURL;
        categoriesAsyncTask = new CategoriesAsyncTask(CategoryFragment.this);
        categoriesAsyncTask.execute(dataUrl);

        return view;
    }

    //==============================================
    //== Option Menu
    //==============================================

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_settings).setVisible(false);
        menu.findItem(R.id.action_save).setVisible(false);
        menu.findItem(R.id.action_add).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_add)
        {
            onAddMenuSelected();
        }

        return false;
    }

    //==============================================
    //== Download Information
    //==============================================

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

    //==============================================
    //== Other Functions
    //==============================================

    private Boolean onAddMenuSelected()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        // Set custom layout
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_new_category, null);
        dialogBuilder.setView(dialogView);

        // Get view elements
        final EditText editText = (EditText) dialogView.findViewById(R.id.dialogCategoryName);

        // Set confirm action
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Log.w("May", "Text: " + editText.getText().toString());
            }
        });

        // set cancel action
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        // Create Dialog
        AlertDialog alertDialog = dialogBuilder.create();
        dialogBuilder.create();
        alertDialog.show();

        return true;
    }
}
