package pl.oldzi.smuggler.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import pl.oldzi.smuggler.Item;
import pl.oldzi.smuggler.R;
import pl.oldzi.smuggler.database.DatabaseHelper;
import pl.oldzi.smuggler.view.SmugglerRecyclerAdapter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProductsActivity extends BaseMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_layout);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<Item> items = databaseHelper.getItems();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean inBossMode = sharedPreferences.getBoolean("bossMode", false);
        RecyclerView productsRecyclerView = (RecyclerView) findViewById(R.id.smugglerRecyclerView);
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SmugglerRecyclerAdapter recyclerAdapter = new SmugglerRecyclerAdapter(items, inBossMode);
        productsRecyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}

