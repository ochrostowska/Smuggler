package pl.oldzi.smuggler;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AddActivity extends AppCompatActivity {

    //Declaring views
    private TextInputEditText editTextId;
    private TextInputEditText editTextCodename;
    private TextInputEditText editTextName;
    private TextInputEditText editTextQuantity;

    private Button addButton;

    //This is our root url
    public static final String ROOT_URL = "http://10.0.3.2/webapp/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);

        //Initializing Views

        editTextId = (TextInputEditText) findViewById(R.id.input_id);
        editTextCodename = (TextInputEditText) findViewById(R.id.input_codename);
        editTextName = (TextInputEditText) findViewById(R.id.input_name);
        editTextQuantity = (TextInputEditText) findViewById(R.id.input_quantity);
        addButton = (Button) findViewById(R.id.addButton);

    }


    private void insertUser() {
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        RegisterAPI api = adapter.create(RegisterAPI.class);

        int nine = Integer.parseInt(editTextQuantity.getText().toString());


        //Defining the method insertuser of our interface
        api.insertUser(

                //Passing the values by getting it from editTexts
                editTextId.getText().toString(),
                editTextCodename.getText().toString(),
                editTextName.getText().toString(),
                nine,

                //Creating an anonymous callback
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        //On success we will read the server's output using bufferedreader
                        //Creating a bufferedreader object
                        BufferedReader reader = null;

                        Log.d("MIMI", "Callback response is : " +response.getBody());
                        //An string to store output from the server - czyli to co echuję
                        String output = "";

                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Displaying the output as a toast
                        Toast.makeText(AddActivity.this, output, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Log.d("MIMI", error.getMessage());
                        Toast.makeText(AddActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void addItem(View view) {
        if (checkIfEmpty(editTextId) || checkIfEmpty(editTextCodename) || checkIfEmpty(editTextName) || checkIfEmpty(editTextQuantity)) {
            Toast.makeText(AddActivity.this, "Fill all the fields", Toast.LENGTH_LONG).show();
        } else {
            insertUser();
        }
    }

    private boolean checkIfEmpty(TextInputEditText editText) {
        if (editText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }
}
//
//package pl.oldzi.smuggler;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.Response.ErrorListener;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//public class AddActivity extends AppCompatActivity {
//
//    EditText id, nameET, codename, quantity;
//    Button addButton, showButton;
//    TextView result;
//    RequestQueue requestQueue;
//    String showUrl = "http://patryk-mnie-uczy.azurewebsites.net/listItems.php";
//    String insertUrl = "http://patryk-mnie-uczy.azurewebsites.net/volleyRegister.php";
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.add_layout);
//        id = (EditText) findViewById(R.id.idET);
//        nameET = (EditText) findViewById(R.id.nameET);
//        codename = (EditText) findViewById(R.id.codenameET);
//        quantity = (EditText) findViewById(R.id.quantityET);
//        addButton = (Button) findViewById(R.id.addButton);
//        showButton = (Button) findViewById(R.id.showButton);
//        result = (TextView) findViewById(R.id.resultTV);
//
//        requestQueue = Volley.newRequestQueue(getApplicationContext());
//
//    }
//
//    public void addItem(View view) {
//
//        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("MIMI", "Response: " + response);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("MIMI", "Another error: " + error);
//            }
//        }) {
//            protected Map<String, String> getParams() throws AuthFailureError {
//                String idS = id.getText().toString();
//                String codenameS = codename.getText().toString();
//                String nameS = nameET.getText().toString();
//                String quantityS = quantity.getText().toString();
//
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("itemid", idS);
//                Log.d("MIMI", "It's id is : " + idS);
//                params.put("codename", codenameS);
//                params.put("name", nameS);
//                Log.d("MIMI", "It's codename is : " + codename.getText().toString());
//
//                params.put("quantity", quantityS);
//
//                return params;
//            }
//        };
//        requestQueue.add(request);
//    }
//
//    public void showItem(View view) {
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, showUrl, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    JSONArray items = response.getJSONArray("smugglerItems");
//                    for( int i = 0; i < items.length(); i++) {
//                        JSONObject item = items.getJSONObject(i);
//
//                        String name = item.getString("name");
//                        Log.d("MIMI", "Name :" + name);
//                        String codename = item.getString("codename");
//
//                        result.append(name + " " + codename + "\n");
//                    }
//                    result.append("===\n");
//                } catch (JSONException e) {
//                    Log.d("MIMI", "Json ex");
//                }
//
//            }
//        }, new ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("MIMI", "Another error: " + error);
//            }
//        });
//        requestQueue.add(jsonObjectRequest);
//    }
//
//}
//
