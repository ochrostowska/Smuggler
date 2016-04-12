package pl.oldzi.smuggler.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;

import pl.oldzi.smuggler.Item;
import pl.oldzi.smuggler.R;
import pl.oldzi.smuggler.SmugglerRecyclerAdapter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ProductsActivity extends BaseMenuActivity {

    private ListView listView;
    private AutoCompleteTextView textView;
    private RecyclerView productsRecyclerView;
    ArrayList<Item> items;
    private SmugglerRecyclerAdapter recyclerAdapter;
//    DatabaseHelper databaseHelper;
//
//    private List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_layout);

        Intent i = getIntent();
        items = (ArrayList<Item>) i
                .getSerializableExtra("items");

        productsRecyclerView = (RecyclerView) findViewById(R.id.smugglerRecyclerView);
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new SmugglerRecyclerAdapter(this, items);
        productsRecyclerView.setAdapter(recyclerAdapter);


//        listView = (ListView) findViewById(R.id.listViewProducts);
//        textView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTV);
 //       showList();
    }
    private void showList(){
        final String[] names = new String[items.size()] ;
        for(int i=0; i<items.size(); i++) {
            names[i] = items.get(i).getName();
        }

        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, names);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.simple_list,names);
        listView.setAdapter(adapter);
        autoCompleteTextViewSettings(names);
        textView.setAdapter(autoCompleteAdapter);

    }

    private void autoCompleteTextViewSettings(final String[] dropDownlist) {
        textView.setDropDownBackgroundResource(R.color.colorPurpleVisible);
        textView.setThreshold(0);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.showDropDown();
            }
        });
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String answerName = textView.getText().toString();
                int index = Arrays.asList(dropDownlist).indexOf(answerName);
                if (index == -1) {
                    Log.d("MAMA2", answerName + "isnt here");
                } else {
                    Log.d("MAMA2", answerName + "is here");

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        textView.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                String answerName = textView.getText().toString();
//                int index = Arrays.asList(dropDownlist).indexOf(answerName);
//                if (index == -1) {
//                    Log.d("MAMA2", answerName + "isnt here");
//                } else {
//                    Log.d("MAMA2", answerName + "is here");
//                }
//            }
//        });



    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}

