package com.mayarafelix.mypantry.spinner;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.mayarafelix.mypantry.R;

import java.util.ArrayList;

public class SimpleSpinnerDialog
{

    ArrayList<?> items;
    Activity context;
    String dTitle;
    OnSpinnerItemClick onSpinerItemClick;
    AlertDialog alertDialog;
    int pos;
    int style;

    public SimpleSpinnerDialog(Activity activity, ArrayList<?> items, String dialogTitle)
    {
        this.items   = items;
        this.context = activity;
        this.dTitle  = dialogTitle;
    }

    public void bindOnSpinerListener(OnSpinnerItemClick onSpinerItemClick1)
    {
        this.onSpinerItemClick = onSpinerItemClick1;
    }

    public void show()
    {
        AlertDialog.Builder adb = new AlertDialog.Builder(this.context);
        View v = this.context.getLayoutInflater().inflate(R.layout.spinner_dialog_layout, (ViewGroup)null);
        TextView rippleViewClose = (TextView)v.findViewById(R.id.close);
        TextView title = (TextView)v.findViewById(R.id.spinerTitle);
        title.setText(this.dTitle);
        ListView listView = (ListView)v.findViewById(R.id.list);
        final EditText searchBox = (EditText)v.findViewById(R.id.searchBox);
        final ArrayAdapter adapter = new ArrayAdapter(this.context, R.layout.spinner_dialog_items, this.items);
        listView.setAdapter(adapter);
        adb.setView(v);
        this.alertDialog = adb.create();
        //this.alertDialog.getWindow().getAttributes().windowAnimations = this.style;
        this.alertDialog.setCancelable(false);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView t = (TextView)view.findViewById(R.id.text1);

                for(int j = 0; j < SimpleSpinnerDialog.this.items.size(); ++j)
                {
                    String teste = SimpleSpinnerDialog.this.items.get(j).toString();

                    if(t.getText().toString().equalsIgnoreCase( teste ) )
                    {
                        SimpleSpinnerDialog.this.pos = j;
                    }
                }

                Object object = SimpleSpinnerDialog.this.items.get(SimpleSpinnerDialog.this.pos);
                SimpleSpinnerDialog.this.onSpinerItemClick.onClick(object, SimpleSpinnerDialog.this.pos);
                SimpleSpinnerDialog.this.alertDialog.dismiss();
            }
        });

        searchBox.addTextChangedListener(new TextWatcher()
        {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                adapter.getFilter().filter(searchBox.getText().toString());
            }
        });

        rippleViewClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SimpleSpinnerDialog.this.alertDialog.dismiss();
            }
        });

        this.alertDialog.show();
    }

}
