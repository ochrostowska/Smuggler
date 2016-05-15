package pl.oldzi.smuggler.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import pl.oldzi.smuggler.Item;
import pl.oldzi.smuggler.R;
import pl.oldzi.smuggler.database.DatabaseHelper;
import pl.oldzi.smuggler.database.MessageEvent;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private Button addButton, sellButton, productsButton, bossButton;
    private DatabaseHelper databaseHelper;
    private List<Item> downloadedItems;
    private EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBackgroundImage();
        eventBus = EventBus.getDefault();
        eventBus.register(this);

        addButton       = (Button) findViewById(R.id.addButton);
        sellButton      = (Button) findViewById(R.id.sellButton);
        productsButton  = (Button) findViewById(R.id.productsButton);
        bossButton      = (Button) findViewById(R.id.bossModeButton);
        databaseHelper = DatabaseHelper.getInstance();
        databaseHelper.downloadData();


    }

    @Subscribe
    public void onEvent(MessageEvent event) {
        downloadedItems = databaseHelper.getItems();
        enableButtons(true);
    }

    public void add(View view) {
        try {
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

            startActivityForResult(intent, 0);

        } catch (Exception e) {

            Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
            Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
            startActivity(marketIntent);
        }
    }

    public void sell(View view) {
        Intent intent = new Intent(MainActivity.this, SellActivity.class);
        startActivity(intent);
    }

    public void products(View view) {
        Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
        intent.putExtra("items", (Serializable) downloadedItems);
        startActivity(intent);
    }

    public void bossMode(View view) {
        Intent intent = new Intent(this, BossActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                Map jsonJavaRootObject = new Gson().fromJson(contents, Map.class);

                boolean conId = jsonJavaRootObject.containsKey("item_id");
                boolean conQuantity = jsonJavaRootObject.containsKey("quantity");
                if (conId && conQuantity) {
                    try {
                        String id = (String) jsonJavaRootObject.get("item_id");
                        String quantity = (String) jsonJavaRootObject.get("quantity");
                        String[] ids = databaseHelper.getIds();
                        int index = Arrays.asList(ids).indexOf(id);

                        //TODO przetestuj czy ta metoda dziala
                        if (index == -1) {
                            startAddActivity(id, quantity);
                        } else {
                            String name = databaseHelper.getName(index);
                            String codename = databaseHelper.getCodeName(index);
                            startAddActivity(id,quantity,name,codename);
                        }
                    } catch (Exception e) {
                        Log.d("Exception", "Exception " + e.getMessage());
                    }
                }
            }
        }
    }

    private void startAddActivity(String... data) {
        if(data.length>=2 && data.length<=4) {
            Intent intent = new Intent(this, AddActivity.class);
            intent.putExtra("id", data[0]);
            intent.putExtra("quantity", data[1]);
            if (data.length == 4) {
                intent.putExtra("name", data[2]);
                intent.putExtra("codename", data[3]);
            }
            startActivity(intent);
        }
    }

    private void enableButtons(boolean enable) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean bossMode = sharedPreferences.getBoolean("bossMode", false);

        if(!bossMode) {
            addButton.setEnabled(bossMode);
            sellButton.setEnabled(bossMode);
        } else {
            addButton.setEnabled(enable);
            sellButton.setEnabled(enable);
        }
        productsButton.setEnabled(enable);
        bossButton.setEnabled(enable);
    }

    private void initBackgroundImage() {
        ImageView background = (ImageView) findViewById(R.id.main_background);
        Glide.with(this)
                .load(R.drawable.smuggler_banner)
                .fitCenter()
                .into(background);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onStop() {
        super.onStop();
        enableButtons(false);
        if(eventBus.isRegistered(this)) eventBus.unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!eventBus.isRegistered(this)) eventBus.register(this);
        databaseHelper.downloadData();
    }
}
