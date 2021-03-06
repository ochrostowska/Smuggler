package pl.oldzi.smuggler.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import pl.oldzi.smuggler.R;

public class BossActivity extends BaseMenuActivity {

    private TextInputEditText passwordET;
    private SharedPreferences sharedPreferences;
    public static final String PASSWORD="kluseczka";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boss_layout);
        passwordET = (TextInputEditText) findViewById(R.id.bossPasswordTV);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

        public boolean isPass() {
            String userPassInput = passwordET.getText().toString().trim();
            return userPassInput.equals(PASSWORD);
        }

    public void enter(View view) {
        if(isPass()) {
            sharedPreferences.edit().putBoolean("bossMode", true).apply();
            finish();
        }
    }

    public void logOut(View view) {
        sharedPreferences.edit().putBoolean("bossMode", false).apply();
        finish();
    }
}