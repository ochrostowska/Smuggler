package pl.oldzi.smuggler.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import pl.oldzi.smuggler.DatabaseHelper;
import pl.oldzi.smuggler.Item;
import pl.oldzi.smuggler.MessageEvent;
import pl.oldzi.smuggler.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    //TODO
    //ADD boolean for checking activity status
    Button addButton, sellButton, productsButton, bossButton;
    public static DatabaseHelper databaseHelper;
    List<Item> downloadedItems;
    int i=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MIMI", "onCreate");
        setContentView(R.layout.activity_main);
        initBackgroundImage();
        EventBus.getDefault().register(this);

        addButton       = (Button) findViewById(R.id.addButton);
        sellButton      = (Button) findViewById(R.id.sellButton);
        productsButton  = (Button) findViewById(R.id.productsButton);
        bossButton      = (Button) findViewById(R.id.bossModeButton);

        databaseHelper = new DatabaseHelper(this);
        databaseHelper.downloadData();


    }

    @Subscribe
    public void onEvent(MessageEvent event) {
        downloadedItems = databaseHelper.getItems();
        enableButtons(true);
        //Log.d("MIMI", "Im here " + i + " time");
        //i++;
        //Toast.makeText(this, event.getMessage(), Toast.LENGTH_LONG).show();
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
                Toast.makeText(this, "Result is " + contents, Toast.LENGTH_LONG).show();

                Map jsonJavaRootObject = new Gson().fromJson(contents, Map.class);

                boolean conId = jsonJavaRootObject.containsKey("item_id");
                boolean conQ = jsonJavaRootObject.containsKey("quantity");
                if (conId && conQ) {
                    Log.d("ZAZI", "no req");
                    try {
                        String id = (String) jsonJavaRootObject.get("item_id");
                        String quantity = (String) jsonJavaRootObject.get("quantity");
                        String[] ids = databaseHelper.getIds();
                        int index = Arrays.asList(ids).indexOf(id);
                        if (index == -1) {
                            Log.d("ZAZI", "No " + id + " id");
                            Intent intent = new Intent(this, AddActivity.class);
                            intent.putExtra("id", id);
                            intent.putExtra("quantity", quantity);
                            startActivity(intent);

                        } else {
                            String name = databaseHelper.getName(index);
                            String codename = databaseHelper.getCodename(index);
                            //textView.setText("Name is " + name + " Codename is " + codename + " Index is " + id);
                            Intent intent = new Intent(this, AddActivity.class);
                            intent.putExtra("name", name);
                            intent.putExtra("codename", codename);
                            intent.putExtra("id", id);
                            intent.putExtra("quantity", quantity);
                            startActivity(intent);

                        }
                    } catch (Exception e) {
                        Log.d("ZAZI", "Exception " + e.getMessage());
                    }
                }


            }
        }
    }

    private void enableButtons(boolean enable) {
        addButton.setEnabled(enable);
        sellButton.setEnabled(enable);
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
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        EventBus.getDefault().register(this);
        databaseHelper.downloadData();
    }

}
