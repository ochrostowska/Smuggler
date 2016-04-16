package pl.oldzi.smuggler.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;

import pl.oldzi.smuggler.R;

public class BossActivity extends BaseMenuActivity {


    private TextInputEditText passwordET;
    private Button enterButton;

    public static final String PASSWORD="kluseczka";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boss_layout);
        passwordET = (TextInputEditText) findViewById(R.id.bossPasswordTV);
        //textView.setText("Hello pretty!");
    }
        public boolean isPass() {
            String userPassInput = passwordET.getText().toString().trim();
            if(userPassInput == PASSWORD) return true;
            return false;
        }

    public void enter(View view) {
        if(isPass()) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            sharedPreferences.edit().putBoolean("bossMode", true).apply();
        }
    }




}