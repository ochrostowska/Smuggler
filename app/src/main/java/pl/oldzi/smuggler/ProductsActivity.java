package pl.oldzi.smuggler;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ProductsActivity extends AppCompatActivity {

    public static final String ROOT_URL = "http://10.0.3.2/webapp/";

    private ListView listView;
    private AutoCompleteTextView textView;
    DatabaseHelper databaseHelper;

    private List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_layout);
        EventBus.getDefault().register(this);

        listView = (ListView) findViewById(R.id.listViewProducts);
        textView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTV);
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.downloadData();

    }

    @Subscribe
    public void onEvent(MessageEvent event) {
        Toast.makeText(this, event.getMessage(), Toast.LENGTH_LONG).show();
        showList();
    }

    private void showList(){
        final String[] names = databaseHelper.getNames();

        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, names);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.simple_list,names);

        autoCompleteTextViewSettings(names);
        listView.setAdapter(adapter);
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
        textView.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
                String answerName = textView.getText().toString();
                int index = Arrays.asList(dropDownlist).indexOf(answerName);
                if (index == -1) {
                    Log.d("MAMA2", answerName + "isnt here");
                } else {
                    Log.d("MAMA2", answerName + "is here");
                }
            }
        });


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

