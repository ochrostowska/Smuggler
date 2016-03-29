package pl.oldzi.smuggler;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProductsActivity extends AppCompatActivity {

    //Root URL of our web service
    public static final String ROOT_URL = "http://10.0.3.2/webapp/";

    //List view to show data
    private ListView listView;

    //List of type books this list will store type Book which is our data model
    private List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_layout);

        //Initializing the listview
        listView = (ListView) findViewById(R.id.listViewProducts);

        //Calling the method that will fetch data
        getItems();

        //Setting onItemClickListener to listview

    }

    private void getItems(){
        //While the app fetched data we are displaying a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Fetching Data","Please wait...",false,false);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        //Creating an object of our api interface
        ItemsAPI api = adapter.create(ItemsAPI.class);

        //Defining the method
        api.getItems(new Callback<List<Item>>() {
            @Override
            public void success(List<Item> list, Response response) {
                //Dismissing the loading progressbar
                loading.dismiss();

                    //Storing the data in our list
                    items = list;
                    Log.d("MIMI", "Items size is : " + items.size());
                    Log.d("MIMI", "Items 1 name is : " + items.get(1).getName());
                    Log.d("MIMI", "Items 0 codename is : " + items.get(0).getCodename());

                    //Calling a method to show the list
                    showList();
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.d("MIMI", error.getMessage());
            }
        });
    }

    //Our method to show list
    private void showList(){
        //String array to store all the book names
        String[] names = new String[items.size()];

        Log.d("MIMI", "Names length is : " + names.length);

        //Traversing through the whole list to get all the names
        for(int i=0; i<items.size(); i++){
            //Storing names to string array
            names[i] = items.get(i).getName();
            Log.d("MIMI", "Names[" +i+"] is " + names[i]);
        }

        //Creating an array adapter for list view
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.simple_list,names);

        //Setting adapter to listview
        listView.setAdapter(adapter);
    }


}

