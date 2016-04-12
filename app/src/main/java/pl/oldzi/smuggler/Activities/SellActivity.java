package pl.oldzi.smuggler.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import pl.oldzi.smuggler.DatabaseHelper;
import pl.oldzi.smuggler.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SellActivity extends BaseMenuActivity {

    private AutoCompleteTextView textView;
    private TextView idTV, codenameTV;
    private TextInputEditText quantityET;
    private Button sellButton;
    private boolean readyTosend=false;
    private String sendName, sendCodename;
    private int sendQuantity, sendId;
    private int index;
    private DatabaseHelper databaseHelper;
    String answerName = "item";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_layout);
        textView = (AutoCompleteTextView) findViewById(R.id.nameAutoCompleteTV);
        idTV = (TextView) findViewById(R.id.textView_id);
        sellButton = (Button) findViewById(R.id.buttonSell);
        codenameTV = (TextView) findViewById(R.id.textView_codename);
        quantityET = (TextInputEditText) findViewById(R.id.textinput_quantity);
        sellButton.setEnabled(false);
        databaseHelper = new DatabaseHelper();
        getNames();

    }

    private void getNames() {
        final String[] names = databaseHelper.getNames();
        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, names);
        textView.setAdapter(autoCompleteAdapter);
        autoCompleteTextViewSettings(names);
    }

    private void autoCompleteTextViewSettings(final String[] dropDownList) {
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
            }

            @Override
            public void afterTextChanged(Editable s) {
                answerName = textView.getText().toString();
                index = Arrays.asList(dropDownList).indexOf(answerName);

                if (index == -1) {
                    idTV        .setText(" ");
                    codenameTV  .setText(" ");

                    sellButton.setEnabled(false);
                    readyTosend = false;

                } else {
                    sendName        = answerName;
                    sendId          = DatabaseHelper.getItemId(index);
                    sendCodename    = DatabaseHelper.getCodename(index);
                    idTV            .setText(String.valueOf(sendId));
                    codenameTV      .setText(sendCodename);

                    sellButton.setEnabled(true);
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
       int numberToSell = 1;
        if(readyTosend) {
            sendQuantity = Integer.parseInt(quantityET.getText().toString());
            DatabaseHelper helper = new DatabaseHelper(this);
            if(sendQuantity<helper.getQuantity(index)) {
                sendQuantity=sendQuantity*(-1);
                helper.sendData(sendId, sendCodename, sendName, sendQuantity);
            } else {
                quantityET.setTextColor(getResources().getColor(R.color.colorPrimary));
                Toast.makeText(this, "Too much", Toast.LENGTH_SHORT).show(); }
        }
        Toast.makeText(this, "Its " + numberToSell, Toast.LENGTH_SHORT).show();
    }
}

