package pl.oldzi.smuggler;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SellActivity extends AppCompatActivity {

    private AutoCompleteTextView textView;
    private List<Item> items;
    private TextView idTV, codenameTV;
    private TextInputEditText quantityET;
    DatabaseHelper databaseHelper;
    private Button sellButton;
    private boolean readyTosend=false;
    private String sendName, sendCodename;
    private int sendQuantity, sendId;
    String answerName = "item";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_layout);
        EventBus.getDefault().register(this);
        textView = (AutoCompleteTextView) findViewById(R.id.nameAutoCompleteTV);
        idTV = (TextView) findViewById(R.id.textView_id);
        sellButton = (Button) findViewById(R.id.sellButton);
        codenameTV = (TextView) findViewById(R.id.textView_codename);
        quantityET = (TextInputEditText) findViewById(R.id.textinput_quantity);
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.downloadData();

    }

    @Subscribe
    public void onEvent(MessageEvent event) {
        Toast.makeText(this, event.getMessage(), Toast.LENGTH_LONG).show();
        getNames();
    }

    private void getNames(){
        final String[] names = databaseHelper.getNames();
        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, names);
        textView.setAdapter(autoCompleteAdapter);
        autoCompleteTextViewSettings(names);
    }

    private void autoCompleteTextViewSettings(final String[] dropDownList) {
//        sellButton.setEnabled(false);
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
                answerName = textView.getText().toString();
                int index = Arrays.asList(dropDownList).indexOf(answerName);

                if (index == -1) {
                    idTV.setText(" ");
                    codenameTV.setText(" ");
                    Log.d("MAMA2", answerName + "isnt here");
                    readyTosend = false;

                } else {
                    sendName = answerName;
                    sendId = databaseHelper.getItemId(index);
                    sendCodename = databaseHelper.getCodename(index);
                    idTV.setText(String.valueOf(sendId));
                    codenameTV.setText(sendCodename);
                    Log.d("MAMA2", answerName + " is here");
                    readyTosend = true;
                }
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void sellItem(View view) {
       int ggg = 1;
        if(readyTosend) {
            ggg = Integer.parseInt(quantityET.getText().toString())*(-1);
            sendQuantity = Integer.parseInt(quantityET.getText().toString())*(-1);
            DatabaseHelper helper = new DatabaseHelper(this);
            helper.sendData(sendId, sendCodename, sendName, sendQuantity);
        }
        else{
            Toast.makeText(this, "We have no " + answerName + " here", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(this, "Its " + ggg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}

