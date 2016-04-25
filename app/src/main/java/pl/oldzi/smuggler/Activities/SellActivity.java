package pl.oldzi.smuggler.Activities;

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

public class SellActivity extends BaseMenuActivity {

    private AutoCompleteTextView textView;
    private TextView idTV, codenameTV;
    private TextInputEditText quantityET;
    private Button sellButton;
    private boolean readyToSend = false;
    private String sendName, sendCodename;
    private int sendQuantity, sendId;
    private int index;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_layout);
        textView    = (AutoCompleteTextView) findViewById(R.id.nameAutoCompleteTV);
        idTV        = (TextView) findViewById(R.id.textView_id);
        sellButton  = (Button) findViewById(R.id.buttonSell);
        codenameTV  = (TextView) findViewById(R.id.textView_codename);
        quantityET  = (TextInputEditText) findViewById(R.id.textinput_quantity);
        sellButton.setEnabled(false);
        databaseHelper = new DatabaseHelper(this);
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
                String answerName = textView.getText().toString();
                index = Arrays.asList(dropDownList).indexOf(answerName);

                if (index == -1) {
                    idTV        .setText(" ");
                    codenameTV  .setText(" ");

                    sellButton.setEnabled(false);
                    readyToSend = false;

                } else {
                    sendName        = answerName;
                    sendId          = DatabaseHelper.getItemId(index);
                    sendCodename    = DatabaseHelper.getCodename(index);
                    idTV            .setText(String.valueOf(sendId));
                    codenameTV      .setText(sendCodename);

                    sellButton.setEnabled(true);
                    readyToSend = true;
                }
            }
        });
    }

    public void sellItem(View view) {
       int numberToSell = 1;
        if(readyToSend) {
            sendQuantity = Integer.parseInt(quantityET.getText().toString());
            //DatabaseHelper helper = new DatabaseHelper(this);
            if(sendQuantity<databaseHelper.getQuantity(index)) {
                sendQuantity=sendQuantity*(-1);
                databaseHelper.sendData(sendId, sendCodename, sendName, sendQuantity);
            } else {
                quantityET.setTextColor(getResources().getColor(R.color.colorPrimary));
                Toast.makeText(this, "We don't have as many items!", Toast.LENGTH_SHORT).show(); }
        }
        Toast.makeText(this, "You sold " + numberToSell + " items!", Toast.LENGTH_SHORT).show();
    }
}

