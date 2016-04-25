package pl.oldzi.smuggler;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DatabaseHelper {
    private static final String ROOT_URL = "https://patryk-mnie-uczy.azurewebsites.net/";
    private static List<Item> items;
    private Context context;

    public DatabaseHelper(Context context) {
        this.context = context;
    }

    public List<Item> getItems() {
        return items;
    }

    public void sendData(int id, String codename, String name, int quantity) {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        ItemsAPI api = adapter.create(ItemsAPI.class);

        api.insertUser(
                id, codename, name, quantity,

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
                        Toast.makeText(context, output, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Log.d("MIMI", error.getMessage());
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }


    public void downloadData() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        ItemsAPI api = adapter.create(ItemsAPI.class);

        api.getItems(new Callback<List<Item>>() {
            @Override
            public void success(List<Item> list, Response response) {
                items = list;
                EventBus.getDefault().post(new MessageEvent("Downloading successful"));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("MIMI", error.getMessage());
            }
        });
    }

    public String[] getNames() {
        final String[] names = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            names[i] = items.get(i).getName();
        }
        return names;
    }

    public String[] getCodenames() {
        final String[] codenames = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            codenames[i] = items.get(i).getCodename();
        }
        return codenames;
    }

    public String[] getIds() {
        final String[] ids = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            ids[i] = String.valueOf(items.get(i).getItem_id());
        }
        return ids;
    }

    public int[] getQuantities() {
        final int[] quantities = new int[items.size()];
        for (int i = 0; i < items.size(); i++) {
            quantities[i] = items.get(i).getQuantity();
        }
        return quantities;
    }

    public String getName(int id) {
        return items.get(id).getName();
    }

    public static String getCodename(int id) {
        return items.get(id).getCodename();
    }

    public static int getQuantity(int id) {
        return items.get(id).getQuantity();
    }

    public static int getItemId(int id) {
        return items.get(id).getItem_id();
    }

}