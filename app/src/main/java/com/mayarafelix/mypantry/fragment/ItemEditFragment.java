package com.mayarafelix.mypantry.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.mayarafelix.mypantry.R;
import com.mayarafelix.mypantry.adapter.ItemAdapter;
import com.mayarafelix.mypantry.model.Category;
import com.mayarafelix.mypantry.model.Item;
import com.mayarafelix.mypantry.spinner.OnSpinnerItemClick;
import com.mayarafelix.mypantry.spinner.SimpleSpinnerDialog;
import com.mayarafelix.mypantry.task.ItemsAsyncTask;
import com.mayarafelix.mypantry.util.UrlManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by mayca on 2018-02-05.
 */

public class ItemEditFragment extends Fragment
{
    private EditText itemName;
    private EditText categoryName;
    private SimpleSpinnerDialog categorySpinner;
    private Button saveButton;
    private Button cancelButton;
    private ImageButton categoryButton;
    ArrayList<Category> categories = new ArrayList<Category>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_edit_item, container, false);
        getActivity().setTitle("New Item");
        setHasOptionsMenu(true);

        // Get elements

        itemName       = view.findViewById(R.id.fragEditItemName);
        categoryName   = view.findViewById(R.id.fragEditItemCategoryName);
        categoryButton = view.findViewById(R.id.fragEditItemCategoryBtn);
        saveButton     = view.findViewById(R.id.fragEditItemSaveBtn);
        cancelButton   = view.findViewById(R.id.fragEditItemCancelBtn);

        categoryName.setEnabled(false);

        // Populate Categories
        populateCategories();

        // Set spinner
        categorySpinner = new SimpleSpinnerDialog(getActivity(),categories,"Simple Spinner Dialog Example");

        categorySpinner.bindOnSpinerListener(new OnSpinnerItemClick()
        {
            @Override
            public void onClick(Object item, int position)
            {
                Category selected = (Category) item;
                categoryName.setText(selected.getName());
            }
        });

        // When click spinner
        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categorySpinner.show();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_settings).setVisible(false);
        menu.findItem(R.id.action_add).setVisible(true);
    }

    public void populateCategories()
    {
        Category category1 = new Category("1", "Category 1");
        Category category2 = new Category("5", "Category 2");
        Category category3 = new Category("2", "Category 3");
        Category category4 = new Category("8", "Category 4");

        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        categories.add(category4);
    }
}
