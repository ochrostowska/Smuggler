package pl.oldzi.smuggler.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import pl.oldzi.smuggler.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class BaseMenuActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected boolean useToolbar() {
        return true;
    }

    @Override
    public void setContentView(int layoutResID) {
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.base_layout, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarX);
        if(useToolbar()) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(R.string.app_name);}
        else {
            toolbar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.info_menu_item) {
            Toast.makeText(this, "Info was clicked!!", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId()==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
