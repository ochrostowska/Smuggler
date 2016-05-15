package pl.oldzi.smuggler.database;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.ResponseBody;
import pl.oldzi.smuggler.Item;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DatabaseHelper {
    private static final String ROOT_URL = "https://patryk-mnie-uczy.azurewebsites.net/";
    public static final String TAG = DatabaseHelper.class.getSimpleName();
    private static List<Item> items;
    private ItemsAPI api;

    private static DatabaseHelper ourInstance;

    public static DatabaseHelper getInstance() {
        if(ourInstance==null) ourInstance = new DatabaseHelper();
        return ourInstance;
    }

    public DatabaseHelper() {
    }

    private void initializeRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(ItemsAPI.class);
    }

    public void sendData(int id, String codename, String name, int quantity) {

        initializeRetrofit();
        Call<ResponseBody> call = api.insertUser(id, codename, name, quantity);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";

                try {
                    output = response.body().string();
                    Log.d(TAG, output);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }


    public void downloadData() {
        initializeRetrofit();
        Call<List<Item>> call = api.getItems();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                items = response.body();
                EventBus.getDefault().post(new MessageEvent("Downloading successful"));
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    public List<Item> getItems() {
        return items;
    }

    // TODO: ZROBIC Z TEGO JEDNA METODE!!!



    public String[] getNames() {
        final String[] names = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            names[i] = items.get(i).getName();
        }
        return names;
    }

    public String[] getCodeNames() {
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

    public String getCodeName(int id) {
        return items.get(id).getCodename();
    }

    public int getQuantity(int id) {
        return items.get(id).getQuantity();
    }

    public int getItemId(int id) {
        return items.get(id).getItem_id();
    }
}

