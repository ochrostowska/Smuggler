package pl.oldzi.smuggler.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Toast;

import pl.oldzi.smuggler.R;
import pl.oldzi.smuggler.database.DatabaseHelper;


public class AddActivity extends BaseMenuActivity {

    private TextInputEditText editTextId;
    private TextInputEditText editTextCodename;
    private TextInputEditText editTextName;
    private TextInputEditText editTextQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);
        Intent intentX  = getIntent();
        String name     = intentX.getStringExtra("name");
        String codename = intentX.getStringExtra("codename");
        String id       = intentX.getStringExtra("id");
        String quantity = intentX.getStringExtra("quantity");

        editTextId       = (TextInputEditText) findViewById(R.id.input_id);
        editTextCodename = (TextInputEditText) findViewById(R.id.input_codename);
        editTextName     = (TextInputEditText) findViewById(R.id.input_name);
        editTextQuantity = (TextInputEditText) findViewById(R.id.input_quantity);
        editTextCodename.setText(codename);
        editTextName.setText(name);
        editTextId.setText(id);
        editTextQuantity.setText(quantity);
    }

    private void insertItem() {
        int    id           = Integer.parseInt(editTextId.getText().toString());
        String codename     = editTextCodename.getText().toString();
        String name         = editTextName.getText().toString();
        int    quantity     = Integer.parseInt(editTextQuantity.getText().toString());
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        databaseHelper.sendData(id, codename, name, quantity);
        finish();
    }

    public void addItem(View view) {
        if (checkIfEmpty(editTextId) || checkIfEmpty(editTextCodename) || checkIfEmpty(editTextName) || checkIfEmpty(editTextQuantity)) {
            Toast.makeText(AddActivity.this, "Fill all the fields", Toast.LENGTH_LONG).show();
        } else {
            insertItem();
        }
    }

    private boolean checkIfEmpty(TextInputEditText editText) {
        // TODO: sprawdz czy dziala
        //return editText.getText().toString().trim().isEmpty();
        return editText.getText().toString().trim().length() <= 0;
    }
}