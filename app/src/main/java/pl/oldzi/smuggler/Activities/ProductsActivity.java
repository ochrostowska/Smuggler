package pl.oldzi.smuggler.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import pl.oldzi.smuggler.Item;
import pl.oldzi.smuggler.R;
import pl.oldzi.smuggler.database.DatabaseHelper;
import pl.oldzi.smuggler.view.SmugglerRecyclerAdapter;

public class ProductsActivity extends BaseMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_layout);
        Intent intent = getIntent();
        List<Item> items;
        try {
            items = (List<Item>) intent.getSerializableExtra("items");
        } catch (Exception e) {
            // Alternatywna wersja pobierania itemsów
            DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
           items = databaseHelper.getItems();
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean inBossMode = sharedPreferences.getBoolean("bossMode", false);

        RecyclerView productsRecyclerView = (RecyclerView) findViewById(R.id.smugglerRecyclerView);
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SmugglerRecyclerAdapter recyclerAdapter = new SmugglerRecyclerAdapter(items, inBossMode);
        productsRecyclerView.setAdapter(recyclerAdapter);
    }
}

