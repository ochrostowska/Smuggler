package pl.oldzi.smuggler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    Button addButton, sellButton, productsButton, bossButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton       = (Button) findViewById(R.id.addButton);
        sellButton      = (Button) findViewById(R.id.sellButton);
        productsButton  = (Button) findViewById(R.id.productsButton);
        bossButton      = (Button) findViewById(R.id.bossModeButton);

    }

    public void add(View view) {
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        startActivity(intent);
    }

    public void sell(View view) {
        Intent intent = new Intent(MainActivity.this, SellActivity.class);
        startActivity(intent);

    }

    public void products(View view) {
        Intent intent = new Intent(MainActivity.this, ProductsActivity.class);

        startActivity(intent);
    }

    public void bossMode(View view) {

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
